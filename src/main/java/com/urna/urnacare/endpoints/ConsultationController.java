package com.urna.urnacare.endpoints;

import com.urna.urnacare.service.ConsultationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {
    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadInvoice(@PathVariable(value="id") Long consultationId, HttpServletResponse response) throws IOException {
        //this.consultationService.downloadPrescription(consultationId, response.getOutputStream());
        byte[] fBytes = this.consultationService.generatePrescription(consultationId);
        response.setHeader("Content-Type", "application/pdf");
        return ResponseEntity.ok(fBytes);
    }
}
