package com.example.microservicecatalogues.Service;

import org.springframework.stereotype.Service;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

          /*  public byte[] generatePdfFromLivres(List<Livre> livres) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
                    PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                    Document document = new Document(pdfDocument);

                    for (Livre livre : livres) {
                        // Ajoutez le contenu du livre au PDF
                        Paragraph paragraph = new Paragraph("Titre : " + livre.getTitre() + "\nDescription : " + livre.getDescription());
                        document.add(paragraph);
                    }

                    document.close();

                    return byteArrayOutputStream.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }*/
}