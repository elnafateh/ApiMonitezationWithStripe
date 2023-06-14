package com.elnafatehh.APIMonitezationWithStripe.customer;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;


@Service
@Data
@Builder

public class StripeService {

    //Stripe Api keys
    private static final String STRIPE_API_KEY = "sk_test_YOUR-SECRET-KEY";
    // Webhook Secret
    private static final String WEBHOOK_SECRET = "whsec_YOUR-KEY";


    public StripeService() {
        String apiKey = STRIPE_API_KEY;
    }

    public Session createCheckoutSession(CheckoutSessionRequest request) throws Exception {
        CustomerCreateParams customerParams = CustomerCreateParams
                .builder()
                .setEmail(request.getEmail())
                .build();
        Customer customer = Customer.create(customerParams);
        SubscriptionCreateParams subscriptionParams = SubscriptionCreateParams
                .builder()
                .setCustomer(customer.getId())
                .addItem(SubscriptionCreateParams.Item.builder().setPrice(request.getPriceId()).build()).build();
        Subscription subscription = Subscription.create(subscriptionParams);
        SessionCreateParams sessionParams = SessionCreateParams
                .builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(SessionCreateParams.LineItem.builder().setPrice("price_YOUR-PRODUCT").build())
                .setSuccessUrl("http://YOUR-WEBSITE/dashboard?session_id={CHECKOUT_SESSION_ID")
                .setCancelUrl("http://YOUR-WEBSITE/error")
                .setCustomer(customer.getId())
                .build();
        RequestOptions requestOptions = RequestOptions
                .builder()
                .setApiKey("ajdsjdk")
                .build();
        return Session.create(sessionParams, requestOptions);

    }

    public Object handleWebhook(String payload, String signature) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(payload, signature, WEBHOOK_SECRET);
        // Process different event types
        if ("checkout.session.completed".equals(event.getType())) {
            EventDataObjectDeserializer eventDataObject = event.getDataObjectDeserializer();
            if (eventDataObject.getObject().isPresent()) {
                Session session = (Session) eventDataObject.getObject().get();
                // Retrieve the required data from the session object
                String customerId = session.getCustomer();
                String subscriptionId = session.getSubscription();
            }

        }
        //Store API keys and CustomerData


        //Generate Api keys
        String apiKey = generateAPIKey();
        String hashedAPIKey = hashAPIKey(apiKey);
return null;
    }

    private String hashAPIKey(String apiKey) {
        // Implement your preferred hashing algorithm
        // For example, you can use Spring Security's BCryptPasswordEncoder or SHA-256
        // Here's an example using SHA-256:
        // return DigestUtils.sha256Hex(apiKey);

        return apiKey;
    }

    private String generateAPIKey() {
        byte[] apiKeyBytes = new byte[16];
        new java.util.Random().nextBytes(apiKeyBytes);
        return new String(apiKeyBytes);
    }
}
