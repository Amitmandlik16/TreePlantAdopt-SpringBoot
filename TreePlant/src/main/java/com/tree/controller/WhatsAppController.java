package com.tree.controller;
import com.tree.entity.TreeOwner;
import com.tree.repository.TreeOwnerepo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class WhatsAppController {

    private final TreeOwnerepo treeOwnerRepository;

    // Twilio Credentials
    public static final String ACCOUNT_SID = "ACdffbe44d72d4e6eef69fb01203b7447a";
	public static final String AUTH_TOKEN = "699174fd0cc518640a16cee6e457634c";
    private static final String TWILIO_WHATSAPP_NUMBER = "whatsapp:+14155238886";

    public WhatsAppController(TreeOwnerepo treeOwnerRepository) {
        this.treeOwnerRepository = treeOwnerRepository;
    }

    // ✅ API to send WhatsApp messages
    @PostMapping("/send-whatsapp")
    public String sendWhatsAppMessagesToAllTreeOwners(@RequestBody MessageRequest request) {
        if (request.getMessage() == null) {
            return "Message is required!";
        }

        sendWhatsAppMessages(request.getMessage());
        return "WhatsApp messages sent successfully to all tree owners!";
    }

    // ✅ Fetch all phone numbers and send WhatsApp message
    public void sendWhatsAppMessages(String messageContent) {
        List<TreeOwner> treeOwners = treeOwnerRepository.findAll();

        for (TreeOwner owner : treeOwners) {
            sendWhatsAppMessage(owner.getMobileNumber(), owner.getFirstName(), messageContent);
        }
    }

    // ✅ Send individual WhatsApp message
    private void sendWhatsAppMessage(String toPhoneNumber, String firstName, String messageContent) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            String whatsappNumber = "whatsapp:" + toPhoneNumber;
            String fullMessage = "Hello " + firstName + ",\n\n" + messageContent + "\n\n"
                    + "🌱 Thank you for being part of the TreeAdoption community!";

            Message message = Message.creator(
                new PhoneNumber(whatsappNumber),
                new PhoneNumber(TWILIO_WHATSAPP_NUMBER),
                fullMessage
            ).create();

            System.out.println("WhatsApp message sent to: " + toPhoneNumber);
        } catch (Exception e) {
            System.out.println("Error sending WhatsApp message to " + toPhoneNumber + ": " + e.getMessage());
        }
    }
}

// ✅ Message Request DTO
class MessageRequest {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
