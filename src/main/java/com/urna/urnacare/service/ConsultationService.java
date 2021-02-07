package com.urna.urnacare.service;

import be.quodlibet.boxable.*;
import be.quodlibet.boxable.line.LineStyle;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.urna.urnacare.domain.Appointment;
import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.ConsultationDto;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.ConsultationMapper;
import com.urna.urnacare.repository.AppointmentRepository;
import com.urna.urnacare.repository.ConsultationRepository;
import com.urna.urnacare.repository.UserRepository;
import com.urna.urnacare.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Optional;


@Service
@Slf4j
@Transactional(readOnly = false)
public class ConsultationService {

	private final ConsultationRepository repository;
	private final ConsultationMapper mapper;
	private final AppointmentRepository appointmentRepository;
	private final UserRepository userRepository;
	private final SpringTemplateEngine templateEngine;

	public ConsultationService(ConsultationRepository repository, ConsultationMapper mapper, AppointmentRepository appointmentRepository, UserRepository userRepository, SpringTemplateEngine templateEngine) {
		this.repository = repository;
		this.mapper = mapper;
		this.appointmentRepository = appointmentRepository;
		this.userRepository = userRepository;
		this.templateEngine = templateEngine;
	}

	public ConsultationDto create(ConsultationDto dto) {
		log.debug("creating consultation {}", dto);
		if(dto.getId() != null) {
			throw new BadRequestAlertException("Consultation cannot have id while creating.",
					"consultation", "idNotNull");
		}
		Consultation consultation = this.mapper.toEntity(dto);
		Consultation consultationSaved = this.repository.save(consultation);
		log.debug("created consultation {}", consultationSaved);
		return this.mapper.toDto(consultationSaved);
	}

	public ConsultationDto update(ConsultationDto dto) {
		if(dto.getId() == null) {
			throw new BadRequestAlertException("Consultation cannot be null while updating.",
					"consultation", "idNull");
		}
		Consultation consultation = this.mapper.toEntity(dto);
		return this.mapper.toDto(this.repository.save(consultation));
	}

	@Transactional(readOnly = true)
	public ConsultationDto getOne(Long id) {
		Optional<Consultation> consultationOptional = this.repository.findById(id);
		if(consultationOptional
				.isPresent()) {
			return this.mapper.toDto(consultationOptional.get());
		}
		return null;
	}

	@Transactional(readOnly = true)
	public byte[] generatePrescription(Long consultationId) throws IOException {
		Appointment appointment = this.appointmentRepository.findByConsultationId(consultationId);
		if(appointment == null) {
			throw new BadRequestAlertException("Invalid consultation Id. Appointment not found.",
					"appointment", "appointmentNotFound");
		}
		Optional<String> optionalUserLogin = SecurityUtils.getCurrentUserLogin();
		Optional<User> optionalUser = this.userRepository.findOneByEmailIgnoreCase(optionalUserLogin.get());
		User user = optionalUser.get();
		if(!(appointment.getPatient().getId() == user.getId() || appointment.getDoctor().getId() == user.getId())) {
			throw new BadRequestAlertException("Illegal access. User does not have the privilege to download this prescription.",
					"consultation", "illegalAccess");
		}
		Optional<Consultation> optionalConsultation = this.repository.findById(consultationId);
		Consultation consultation = optionalConsultation.get();
		Locale locale = Locale.forLanguageTag("en");
		Context context = new Context(locale);
		context.setVariable("appointment", appointment);
		context.setVariable("consultation", consultation);
		String html = templateEngine.process("pdf/prescription", context);
		System.out.println(html);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PdfRendererBuilder builder = new PdfRendererBuilder();
		builder.withHtmlContent(html, "http://localhost:4200");
		builder.useFastMode();
		builder.toStream(os);
		builder.run();
		return os.toByteArray();

	}

	@Transactional(readOnly = false)
	public PDDocument downloadPrescription(Long consultationId, OutputStream os) throws IOException {
		PDFont fontPlain = PDType1Font.HELVETICA;
		PDFont fontBold = PDType1Font.HELVETICA_BOLD;
		PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
		PDFont fontMono = PDType1Font.COURIER;

		PDDocument document = new PDDocument();
		PDPage page = new PDPage(PDRectangle.A4);
		PDRectangle rect = page.getMediaBox();
		document.addPage(page);
		PDPageContentStream cos = new PDPageContentStream(document, page);

		//Dummy Table
		float margin = 50;
		// starting y position is whole page height subtracted by top and bottom margin
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		// we want table across whole page width (subtracted by left and right margin ofcourse)
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

		boolean drawContent = true;
		float yStart = yStartNewPage;
		float bottomMargin = 50;
		// y position is your coordinate of top left corner of the table
		float yPosition = 800;

		BaseTable table = new BaseTable(yPosition, yStartNewPage,
				bottomMargin, tableWidth, margin, document, page, true, drawContent);

		// the parameter is the row height
		Row<PDPage> headerRow = table.createRow(100);
		// the first parameter is the cell width
		Cell<PDPage> cell = headerRow.createCell(100, "Header");
		cell.setFont(fontBold);
		cell.setFontSize(20);
		// vertical alignment
		cell.setValign(VerticalAlignment.MIDDLE);
		// border style
		cell.setLeftPadding(50);
		cell.setRightPadding(5);
		cell.setTopPadding(5);
		cell.setBottomPadding(5);
		cell.setFillColor(Color.lightGray);
		cell.setBottomBorderStyle(null);
		table.addHeaderRow(headerRow);

		Row<PDPage> row = table.createRow(20);
		cell = row.createCell(30, "black left plain");
		cell.setFontSize(15);
		cell = row.createCell(70, "black left bold");
		cell.setFontSize(15);
		cell.setFont(fontBold);

		row = table.createRow(20);
		cell = row.createCell(50, "red right mono");
		cell.setTextColor(Color.RED);
		cell.setFontSize(15);
		cell.setFont(fontMono);
		// horizontal alignment
		cell.setAlign(HorizontalAlignment.RIGHT);
		cell.setBottomBorderStyle(new LineStyle(Color.RED, 5));
		cell = row.createCell(50, "green centered italic");
		cell.setTextColor(Color.GREEN);
		cell.setFontSize(15);
		cell.setFont(fontItalic);
		cell.setAlign(HorizontalAlignment.CENTER);
		cell.setBottomBorderStyle(new LineStyle(Color.GREEN, 5));

		row = table.createRow(20);
		cell = row.createCell(40, "rotated");
		cell.setFontSize(15);
		// rotate the text
		cell.setTextRotated(true);
		cell.setAlign(HorizontalAlignment.RIGHT);
		cell.setValign(VerticalAlignment.MIDDLE);
		// long text that wraps
		cell = row.createCell(30, "long text long text long text long text long text long text long text");
		cell.setFontSize(12);
		// long text that wraps, with more line spacing
		cell = row.createCell(30, "long text long text long text long text long text long text long text");
		cell.setFontSize(12);
		cell.setLineSpacing(2);

		table.draw();

		float tableHeight = table.getHeaderAndDataHeight();
		System.out.println("tableHeight = "+tableHeight);

		// close the content stream
		cos.close();

		// Save the results and ensure that the document is properly closed:
		document.save(os);

		document.close();
		return document;
	}
}
