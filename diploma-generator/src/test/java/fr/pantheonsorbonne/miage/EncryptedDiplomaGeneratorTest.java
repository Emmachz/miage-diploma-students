package fr.pantheonsorbonne.miage;


 import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayOutputStream;

 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.nio.file.Files;
 import java.nio.file.Path;

 import org.junit.jupiter.api.Test;

 import com.itextpdf.text.DocumentException;
 import com.itextpdf.text.pdf.PdfReader;
 import com.itextpdf.text.pdf.PdfStamper;

 public class EncryptedDiplomaGeneratorTest extends DiplomaGeneratorTest {



 	@Test
 	public void testEncryptedPdfTest() throws IOException, DocumentException {

 		Student stu = new Student(1, "Nicolas", "", "nico");
 		DiplomaGenerator generator = new MiageDiplomaGenerator(stu, DiplomaGeneratorTest.currentDate);
 		AbstractDiplomaGenerator encryptedGenerator = new EncryptedDiplomaGeneratorDecorator(generator, "abc");
 		FileGenerator<AbstractDiplomaGenerator> adapter = new DiplomaFileAdapter(encryptedGenerator);

 		Path tempFileEncrypted = Files.createTempFile("prefix", ".pdf");
 		Path tempFileDecrypted = Files.createTempFile("prefix", ".pdf");

 		adapter.generateFile(tempFileEncrypted.toString());

 		PdfReader reader = new PdfReader(tempFileEncrypted.toString(), "abc".getBytes());

 		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(tempFileDecrypted.toString()));
 		stamper.close();
 		reader.close();


 		ByteArrayOutputStream generatedImageData = new ByteArrayOutputStream();

 		System.out.println(tempFileDecrypted);

 		writePDFImageRasterBytes(tempFileDecrypted.toFile(), generatedImageData);
		assertArrayEquals(generatedImageData.toByteArray(), generatedImageData.toByteArray());
 		stamper.close();
 		reader.close();

 	}
 }