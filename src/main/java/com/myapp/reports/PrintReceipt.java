/**
 * Created By Anamika Pandey
 */
package com.myapp.reports;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.myapp.modal.InvoiceReceiptDetails;
import com.myapp.modal.ReceiptMaster;
import com.myapp.utilities.NumberToWords;
import com.myapp.utilities.ReceiptModal;

public class PrintReceipt {
	public static final String FOOTER = "src/main/resources/static/images/receipt_footer.jpg";
    public static final String HEADER = "src/main/resources/static/images/svp_receipt_logo.JPG";
    
    public static PdfPCell createImageCell(String path) throws DocumentException, IOException {    	
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img, true);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
	
	public static ByteArrayInputStream generateReceipt(ReceiptModal receiptModal,String headerName,String subHeaderName) throws IOException, DocumentException{
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
        	PdfPTable mainTable = new PdfPTable(1);
        	mainTable.setWidthPercentage(100);
        	mainTable.setWidths(new int[]{100});
        	PdfPCell hcell;
        	hcell = new PdfPCell(new Phrase("\n"));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        mainTable.addCell(hcell);
        	mainTable.addCell(generateCopies(receiptModal,1));
        	
	        hcell = new PdfPCell(new Phrase("\n\n--------------------------------------------------------------------------------------------------------------------------------\n\n\n"));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        mainTable.addCell(hcell);
        	mainTable.addCell(generateCopies(receiptModal,2));
        	PdfWriter.getInstance(document, out);
            document.open();
            document.add(mainTable);
            document.close();
            
        } catch (DocumentException ex) {
            Logger.getLogger(GenerateBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
	
	private static PdfPTable generateCopies(ReceiptModal receiptModal,int count) {
		try {
			PdfPTable pTable = new PdfPTable(1);
			pTable.setWidthPercentage(100);
			pTable.setWidths(new int[]{100});
			pTable.getDefaultCell().setBorder(0);
	    	
			PdfPTable table = new PdfPTable(8);
	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{2,27,10,5,16,12,25,2});
	
	        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA);
	        headFont2.setSize(12);
	        
	        Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC);
	    	
	    	PdfPCell mcell;
	        if(count==1) {
	        	mcell = new PdfPCell(new Phrase("Buyer Copy",new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC,BaseColor.RED)));
		        mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        mcell.setBorder(0);
		        pTable.addCell(mcell);
	        }else if(count==2) {
	        	mcell = new PdfPCell(new Phrase("Office Copy",new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC,BaseColor.RED)));
		        mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        mcell.setBorder(0);
		        pTable.addCell(mcell);
	        }
	    	pTable.addCell(createImageCell(HEADER));
	        
	        mcell = new PdfPCell(new Phrase("CL-DEPOT :-"+receiptModal.getDepotName(),headFont));
	        mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        mcell.setBorder(0);
	        pTable.addCell(mcell);
	        
	        //cell 1
	        mcell = new PdfPCell(new Phrase(""));
	        mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        mcell.setBorder(0);
	        table.addCell(mcell);
	        //cell 2
	        table.addCell(createImageCell("src/main/resources/static/images/recipt_note.jpg"));
	        //cell 3
	        mcell = new PdfPCell(new Phrase(""));
	        mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        mcell.setBorder(0);
	        table.addCell(mcell);
	        //cell 4
	        table.addCell(createImageCell("src/main/resources/static/images/rupees.jpg"));
	        
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	        String strDate= formatter.format(receiptModal.getReceiptDate());
	        
	        String totAmt1 = "" + BigDecimal.valueOf(receiptModal.getAmount()).toPlainString() ;
	        String[] amtArr =totAmt1.split("\\.");
	        String totAmtWords="";
	        if (amtArr.length>1 && Integer.parseInt(amtArr[1]) > 0) {
				totAmtWords = NumberToWords.convertNumToWord((long) Double
						.parseDouble(amtArr[0]))
						+ " and "
						+ NumberToWords.convertNumToWord((long) Double
								.parseDouble(amtArr[1])) + " Paise"+" Only";
			} else {
				totAmtWords = NumberToWords.convertNumToWord((long) Double
						.parseDouble(amtArr[0]))+" Only";
			}
	        PdfPCell hcell;
	        //cell 5
	        hcell = new PdfPCell(new Phrase(""+ String.format("%.2f", receiptModal.getAmount()),headFont));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setNoWrap(true);
	        table.addCell(hcell);
	        //cell 6
	        hcell = new PdfPCell(new Phrase(""));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        table.addCell(hcell);
	        //cell 7
	        hcell = new PdfPCell(new Phrase("No."+receiptModal.getReceiptNumber()+"\nDate."+strDate , new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        hcell.setBorder(0);
	        hcell.setNoWrap(true);
	        table.addCell(hcell);
	        //cell 8
	        hcell = new PdfPCell(new Phrase(""));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        table.addCell(hcell);
	        
	        pTable.addCell(table);
	         
	        mcell = new PdfPCell(new Phrase("\n", headFont));
	        mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        mcell.setBorder(0);
	        pTable.addCell(mcell);
	        
	        PdfPTable tableSubj = new PdfPTable(3);
	        tableSubj.setWidthPercentage(100);
	        tableSubj.setWidths(new int[]{1,29,70});
	        
	        hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        tableSubj.addCell(hcell);
	        tableSubj.addCell(createImageCell("src/main/resources/static/images/sub.jpg"));
	        hcell = new PdfPCell(new Phrase(""+receiptModal.getCustomerName(), 
	        		new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
	        hcell.setBorder(0);
	        tableSubj.addCell(hcell);
	        
	        hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        tableSubj.addCell(hcell);
	        
	        hcell = new PdfPCell(new Phrase("................................................................................................................................................................................", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        hcell.setColspan(2);
	        tableSubj.addCell(hcell);
	        
	        hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        tableSubj.addCell(hcell);
	        
	        hcell = new PdfPCell(new Phrase("the sum of Rupees "+totAmtWords, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setColspan(2);
	        hcell.setBorder(0);
	        tableSubj.addCell(hcell); 
	        
	        hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        tableSubj.addCell(hcell); 
	        
	        hcell = new PdfPCell(new Phrase("................................................................................................................................................................................",
	        		new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        hcell.setColspan(2);
	        tableSubj.addCell(hcell);
	        String subj=" vide "+receiptModal.getPaymentMode().toUpperCase()+" and "+receiptModal.getPaymentMode().toUpperCase()+" No.";
	        if(receiptModal.getPaymentMode() != null && (receiptModal.getPaymentMode().equalsIgnoreCase("Cheque") ||
	        		receiptModal.getPaymentMode().equalsIgnoreCase("RTGS") ||
	        		receiptModal.getPaymentMode().equalsIgnoreCase("NEFT")) &&
                    receiptModal.getRtgsUtrNo()!=null) {
	        	subj=subj+""+receiptModal.getRtgsUtrNo();
	        	subj=subj+" against invoice No."+receiptModal.getInvoiceNumber()+".";
	        }else {
	        	subj=subj+" against invoice No."+receiptModal.getInvoiceNumber()+".";
	        }
	        hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        tableSubj.addCell(hcell); 
	        
	        hcell = new PdfPCell(new Phrase(subj,new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        hcell.setColspan(2);
	        tableSubj.addCell(hcell);
	        
	        hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        tableSubj.addCell(hcell);
	        
	        hcell = new PdfPCell(new Phrase("................................................................................................................................................................................", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        hcell.setColspan(2);
	        tableSubj.addCell(hcell);
	        
	        pTable.addCell(tableSubj);
	        pTable.addCell(createImageCell(FOOTER));
	        return pTable;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
