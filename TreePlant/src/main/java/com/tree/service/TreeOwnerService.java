package com.tree.service;

import com.tree.dto.ForgotPasswordRequest;
import com.tree.entity.Tree;
import com.tree.entity.TreeOwner;
import com.tree.repository.TreeOwnerepo;
import com.tree.repository.TreeRepository;
import com.tree.repository.TreeScanRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@Service
public class TreeOwnerService {

	public static final String ACCOUNT_SID = "ACdffbe44d72d4e6eef69fb01203b7447a";
	public static final String AUTH_TOKEN = "699174fd0cc518640a16cee6e457634c";

	@Autowired
	private TreeOwnerepo treeOwnerepo;

	@Autowired
	private TreeRepository treeRepository;

	@Autowired
	private TreeScanRepository treeScanRepository;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image.treeowners}")
	private String treeownerspath;

	// ✅ Login logic
	public TreeOwner login(String username, String password) {
		return treeOwnerepo.findByUsername(username).filter(owner -> owner.getPassword().equals(password)).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
	}

	// ✅ Register new tree owner
	public TreeOwner createOwner(MultipartFile image,TreeOwner treeOwner) throws IOException {
		treeOwner.setTotalTrees(0);
		treeOwner.setTotalRewards(0);
		String imgname=fileService.uploadImage(treeownerspath, image);	
		treeOwner.setProfileImg(imgname);
		return treeOwnerepo.save(treeOwner);
	}

	// ✅ Fetch tree owner by ID
	public TreeOwner getTreeOwnerById(long id) {
		return treeOwnerepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tree Owner not found with ID: " + id));
	}

	// ✅ Update tree owner details
	public TreeOwner updateTreeOwner(long id, TreeOwner treeOwnerDetails) {
		TreeOwner treeOwner = getTreeOwnerById(id);

		treeOwner.setUsername(treeOwnerDetails.getUsername());
		treeOwner.setFirstName(treeOwnerDetails.getFirstName());
		treeOwner.setMiddleName(treeOwnerDetails.getMiddleName());
		treeOwner.setLastName(treeOwnerDetails.getLastName());
		treeOwner.setProfileImg(treeOwnerDetails.getProfileImg());
		treeOwner.setCountry(treeOwnerDetails.getCountry());
		treeOwner.setState(treeOwnerDetails.getState());
		treeOwner.setTaluka(treeOwnerDetails.getTaluka());
		treeOwner.setDistrict(treeOwnerDetails.getDistrict());
		treeOwner.setVillage(treeOwnerDetails.getVillage());
		treeOwner.setPincode(treeOwnerDetails.getPincode());
		treeOwner.setLongitude(treeOwnerDetails.getLongitude());
		treeOwner.setLatitude(treeOwnerDetails.getLatitude());
		treeOwner.setDOB(treeOwnerDetails.getDOB());
		treeOwner.setLandmark(treeOwnerDetails.getLandmark());
		treeOwner.setMobileNumber(treeOwnerDetails.getMobileNumber());
		treeOwner.setEmail(treeOwnerDetails.getEmail());

		return treeOwnerepo.save(treeOwner);
	}

	// ✅ Update password logic
	public String updatePassword(String username, String oldPassword, String newPassword) {
		TreeOwner owner = treeOwnerepo.findByUsername(username).filter(o -> o.getPassword().equals(oldPassword))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid credentials or user not found"));

		owner.setPassword(newPassword);
		treeOwnerepo.save(owner);
		return "Password updated successfully";
	}

	public Map<String, Boolean> deleteTreeOwner(Long id) {
		// Fetch the TreeOwner by ID
		TreeOwner treeOwner = getTreeOwnerById(id);

		// Fetch all trees belonging to the owner
		List<Tree> trees = treeRepository.findByTreeOwnerId(id);

		// Delete all TreeScan entries related to each Tree
		for (Tree tree : trees) {
			treeScanRepository.deleteByTreeId(tree.getId()); // Delete scans for this tree
		}

		// Delete all trees belonging to this owner
		treeRepository.deleteByTreeOwnerId(id);

		// Now delete the TreeOwner
		treeOwnerepo.delete(treeOwner);

		// Return response
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// ✅ Forgot password logic
	public String forgotPassword(ForgotPasswordRequest request) {
		TreeOwner treeOwner = treeOwnerepo.findByUsernameAndEmail(request.getUsername(), request.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

		// Generate a temporary password
		String tempPassword = UUID.randomUUID().toString().substring(0, 8);

		// Update the password in the database
		treeOwner.setPassword(tempPassword);
		treeOwnerepo.save(treeOwner);

		// Send email with new password
		sendEmail(treeOwner.getEmail(), treeOwner.getUsername(), treeOwner.getFirstName(), treeOwner.getLastName(),
				tempPassword);
		sendSms(treeOwner.getMobileNumber(), treeOwner.getUsername(), treeOwner.getFirstName(), treeOwner.getLastName(),
				tempPassword);

		return "A temporary password has been sent to your email.";
	}

	private void sendEmail(String toEmail, String userName, String firstName, String lastName, String tempPassword) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(toEmail);
			helper.setSubject("🌳 TreeAdoption - Password Reset Request");

			// ✅ TreeAdoption Logo URL (Replace with actual hosted image)
			String logoUrl = "https://clipart-library.com/images/8ixrrRA4T.png"; // 🔄 Update with actual image URL

			// ✅ HTML Email Content
			String emailContent = "<!DOCTYPE html>" + "<html><head><style>"
					+ "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }"
					+ ".container { max-width: 500px; background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); text-align: center; }"
					+ ".logo { width: 100px; margin-bottom: 15px; }"
					+ ".header { font-size: 22px; font-weight: bold; color: #2E7D32; }"
					+ ".content { font-size: 16px; color: #333; margin: 15px 0; }"
					+ ".password { font-size: 22px; font-weight: bold; color: #2E7D32; background: #f0f0f0; padding: 10px; display: inline-block; border-radius: 5px; }"
					+ ".button { background-color: #2E7D32; color: white; padding: 10px 15px; text-decoration: none; border-radius: 5px; display: inline-block; margin-top: 10px; font-size: 16px; }"
					+ ".footer { font-size: 14px; color: #777; margin-top: 20px; }" + "</style></head><body>"
					+ "<div class='container'>" + "<img src='" + logoUrl + "' class='logo' alt='TreeAdoption Logo' />"
					+ "<div class='header'>🌳 Password Reset Request</div>" + "<div class='content'>Hello <b>"
					+ firstName + " " + lastName + "</b><br> (<b>" + "UserName :" + userName + "</b>),</div>"
					+ "<div class='content'>We received a request to reset your password for your TreeAdoption account.</div>"
					+ "<div class='content'>Your new temporary password is:</div>" + "<div class='password'>"
					+ tempPassword + "</div>"
					+ "<div class='content'>Please log in using this temporary password and change it immediately for security.</div>"
					+ "<div class='content'><a href='http://localhost:4200/login' class='button'>Login to TreeAdoption</a></div>"
					+ "<div class='footer'>If you didn’t request this, please contact support.</div>"
					+ "<div class='footer'>🌱 Thank you for being part of the TreeAdoption community!</div>"
					+ "</div></body></html>";

			helper.setText(emailContent, true); // Enable HTML format
			mailSender.send(message);

		} catch (MessagingException e) {
			System.out.println("Error sending email: " + e.getMessage());
		}
	}

	public static void sendSms(String toPhoneNumber, String userName, String firstName, String lastName,
			String tempPassword) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber("+91" + toPhoneNumber), // Recipient
				new PhoneNumber("+18483445411"), // Twilio Phone Number
				"\n\n🌳 TreeAdoption: \nHello " +firstName +" "+ lastName+ "\n UserName :"+userName+"\nYour temporary password is " + tempPassword + ". Please update it after logging in.")
				.create();

		System.out.println("SMS sent! SID: " + message.getSid());
	}

}
