package org.insanedevelopment.nx.sx.sxbootdatcreatorwebui;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {

	private static Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {
		return "uploadForm";
	}

	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<Resource> handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

		Resource convertedFile = new ByteArrayResource(convertFile(file));
		ResponseEntity<Resource> result = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"boot.dat\"").body(convertedFile);
		return result;
	}

	private byte[] convertFile(MultipartFile file) {
		try {
			return SxBootDatCreator.convertPayloadToBootDat(file.getBytes());
		} catch (IOException e) {
			throw new FileConversionException(e);
		}
	}

	@ExceptionHandler(FileConversionException.class)
	public ResponseEntity<?> handleStorageFileNotFound(FileConversionException exc) {
		LOGGER.error("Error converting file", exc);
		return ResponseEntity.badRequest().build();
	}

}
