package com.urna.urnacare.endpoints;

import com.urna.urnacare.config.ApplicationProperties;
import com.urna.urnacare.dto.PayUMoneyPaymentResponseDTO;
import com.urna.urnacare.dto.PaymentRequestDTO;
import com.urna.urnacare.dto.PaymentResponseBundle;
import com.urna.urnacare.service.PaymentService;
import com.urna.urnacare.util.GenericUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final ApplicationProperties applicationProperties;

    public PaymentController(PaymentService paymentService, ApplicationProperties applicationProperties) {
        this.paymentService = paymentService;
        this.applicationProperties = applicationProperties;
    }

    @GetMapping("/vf/init/{appointmentRequestId}")
    public ResponseEntity<PaymentRequestDTO> initVistingFeesPayment(@PathVariable Long appointmentRequestId) {
        PaymentRequestDTO paymentRequestDTO = this.paymentService.visitingFeesInit(appointmentRequestId);
        return ResponseEntity.ok(paymentRequestDTO);
    }
    @PostMapping("/vfcallback")
    public void vfcallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PaymentResponseBundle bundle = getPaymentResponseBundle(request);
        String status = this.paymentService.handleVisitingFeesPaymentResponse(
                bundle.getResponseDTO(), bundle.getResponseString());
        response.sendRedirect(this.applicationProperties.getPayment().getVfcallbackResponseUrl() + "?status=" + status + "&mode=vf");
    }

        @GetMapping("/init/{orderId}")
    public ResponseEntity<PaymentRequestDTO> initPayment(@PathVariable Long orderId) {
        PaymentRequestDTO paymentRequestDTO = this.paymentService.paymentInit(orderId);
        return ResponseEntity.ok(paymentRequestDTO);
    }

    @PostMapping("/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PaymentResponseBundle bundle = this.getPaymentResponseBundle(request);
        String status = this.paymentService.handlePaymentResponse(bundle.getResponseDTO(), bundle.getResponseString());
        response.sendRedirect(this.applicationProperties.getPayment().getCallbackResponseUrl() + "?status=" + status);
    }

    private PaymentResponseBundle getPaymentResponseBundle(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
        String paymentResponse = "{";
        PayUMoneyPaymentResponseDTO responseDTO = new PayUMoneyPaymentResponseDTO();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            paymentResponse+= key + ":" + value + ",";
            if(key.equals("mihpayid")) {
                responseDTO.setMihpayid(value);
            } else if(key.equals("txnid")) {
                responseDTO.setTxnid(value);
            } else if(key.equals("status")) {
                responseDTO.setStatus(value);
            } else if(key.equals("amount")) {
                responseDTO.setAmount(value);
            } else if(key.equals("amount")) {
                responseDTO.setAmount(value);
            } else if(key.equals("mode")) {
                responseDTO.setMode(value);
            } else if(key.equals("productinfo")) {
                responseDTO.setProductinfo(value);
            } else if(key.equals("firstname")) {
                responseDTO.setFirstname(value);
            } else if(key.equals("email")) {
                responseDTO.setEmail(value);
            } else if(key.equals("phone")) {
                responseDTO.setPhone(value);
            } else if(key.equals("udf1")) {
                responseDTO.setUdf1(value);
            }else if(key.equals("hash")) {
                responseDTO.setHash(value);
            }
        }
        paymentResponse+= "}";

        PaymentResponseBundle paymentResponseBundle = new PaymentResponseBundle();
        paymentResponseBundle.setResponseDTO(responseDTO);
        paymentResponseBundle.setResponseString(paymentResponse);
        return paymentResponseBundle;
    }

    public static void main(String[] args) {
        /*
        isConsentPayment:0
        mihpayid:9084083149
        mode:CC
        status:success
        unmappedstatus:captured
        key:h6nS35ea
        txnid:629227808433506
        amount:230.00
        addedon:2020-11-30 12:12:49
        productinfo:Medicine purchase against order id:100
        firstname:Kalyan
        lastname:
        address1:
        address2:
        city:
        state:
        country:
        zipcode:
        email:kalyandas75@gmail.com
        phone:9593095978
        udf1:103
        udf2:
        udf3:
        udf4:
        udf5:
        udf6:
        udf7:
        udf8:
        udf9:
        udf10:
        hash:94b4c03d8c46cc3f75188df4158e95b817c07f30f4dbb5f1dbccf1d20fbd61d08881fa5e78dee5670a919194853b1d4183199ae55745a7abb52553534bf72c50
        field1:422752539846
        field2:020001
        field3:669819051918070
        field4:aGVONmRJT2ZhNnpwQXVWNjhqb1c=
                field5:05
        field6:
        field7:AUTHPOSITIVE
        field8:
        field9:
        giftCardIssued:true
        PG_TYPE:HDFCPG
        encryptedPaymentId:8F961F3A6FB10AFA508C29C0F52C0908
        bank_ref_num:669819051918070
        bankcode:VISA
        error:E000
        error_Message:No Error
        name_on_card:Test
        cardnum:401200XXXXXX1112
        cardhash:This field is no longer supported in postback params.
                amount_split:{"PAYU":"230.00"}
        payuMoneyId:250669322
        discount:0.00
        net_amount_debit:230
        */

        String receivedHash = "94b4c03d8c46cc3f75188df4158e95b817c07f30f4dbb5f1dbccf1d20fbd61d08881fa5e78dee5670a919194853b1d4183199ae55745a7abb52553534bf72c50";
        String calculatedUnhashed = "SBZiWH9Yon|success||||||||||103|kalyandas75@gmail.com|Kalyan|Medicine purchase against order id:100|230.00|629227808433506|h6nS35ea";
        String calculatedHash = GenericUtil.calculateHash("SHA-512", calculatedUnhashed);
        System.out.println(receivedHash.equals(calculatedHash));
    }
}
