package com.elnafatehh.APIMonitezationWithStripe.customer;

import com.elnafatehh.APIMonitezationWithStripe.customer.CheckoutSessionRequest;
import com.elnafatehh.APIMonitezationWithStripe.customer.StripeService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class StripeController {
    private final StripeService service;

   @PostMapping("/checkout")
   public ResponseEntity<?> createCheckoutSession(@RequestBody CheckoutSessionRequest request ){
       try {
           Session session = service.createCheckoutSession(request);
           return ResponseEntity.ok(session);
       } catch (Exception e){
           // Handle Stripe exception and return appropriate error response
           return ResponseEntity.status(500).body("Error creating checkout session: " + e.getMessage());
       }
   }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) throws SignatureVerificationException {
       return ResponseEntity.ok(service.handleWebhook(payload, signature));

    }
    @GetMapping("/apiCall")
    public ResponseEntity<?> makeApiCall(@RequestParam String apiKey) throws Exception {
        // Call the service method to make an API call
        // ...

        return ResponseEntity.ok().build();
    }


}
