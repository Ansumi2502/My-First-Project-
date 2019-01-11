/**
 * Created By Anamika Pandey
 */
package com.myapp.reports;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.myapp.modal.BillDetails;
import com.myapp.modal.InvoiceReceiptDetails;
import com.myapp.modal.ItemDistributionDetails;
import com.myapp.modal.ReceiptMaster;
import com.myapp.service.ReceiptService;
import com.myapp.utilities.NumberToWords;

import javassist.bytecode.Descriptor.Iterator;

public class PrintBulkReceipt {
	public static final String FOOTER = "src/main/resources/static/images/receipt_footer.jpg";
    public static final String HEADER = "src/main/resources/static/images/receipt_header.jpg";
    @Autowired
	private static ReceiptService receiptService;
    
    public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
    	
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img, true);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
	
	public static ByteArrayInputStream generateReceipt(Collection<InvoiceReceiptDetails> invoiceReceiptColls,String headerName,String subHeaderName) throws IOException, DocumentException{
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
        	PdfPTable mainReceiptTable = new PdfPTable(1);
        	mainReceiptTable.setWidthPercentage(100);
        	mainReceiptTable.setWidths(new int[]{100});
        	for(InvoiceReceiptDetails invoiceReceiptDetails:invoiceReceiptColls) {
	        	PdfPTable mainTable = new PdfPTable(1);
	        	mainTable.setWidthPercentage(100);
	        	mainTable.setWidths(new int[]{100});
	        	mainTable.addCell(generateCopies(invoiceReceiptDetails,1));
	        	PdfPCell hcell;
		        hcell = new PdfPCell(new Phrase("\n--------------------------------------------------------------------------------------------------------------------------------\n\n"));
		        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		        hcell.setBorder(0);
		        mainTable.addCell(hcell);
	        	mainTable.addCell(generateCopies(invoiceReceiptDetails,2));
	        	mainReceiptTable.addCell(mainTable);
        	}
        	PdfWriter.getInstance(document, out);
            document.open();
            document.add(mainReceiptTable);
            document.close();
            
        } catch (DocumentException ex) {
            Logger.getLogger(GenerateBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
	
	private static PdfPTable generateCopies(InvoiceReceiptDetails invoiceReceiptDetailsObj,int count) {
		try {
			PdfPTable pTable = new PdfPTable(1);
			pTable.setWidthPercentage(100);
			pTable.setWidths(new int[]{100});
			pTable.getDefaultCell().setBorder(0);
	    	
			PdfPTable table = new PdfPTable(8);
	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{2,27,10,5,10,12,31,2});
	
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
	    
	        mcell = new PdfPCell(new Phrase("CL-DEPOT :-"+invoiceReceiptDetailsObj.getBillDetails().getDepotMaster().getName(),headFont));
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
	        String strDate= formatter.format(invoiceReceiptDetailsObj.getReceiptMaster().getReceiptDate());
	        
	        String totAmt1 = "" + BigDecimal.valueOf(invoiceReceiptDetailsObj.getReceiptMaster().getAmount()).toPlainString() ;
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
	        hcell = new PdfPCell(new Phrase(""+ String.format("%.2f", invoiceReceiptDetailsObj.getReceiptMaster().getAmount()),headFont));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        table.addCell(hcell);
	        //cell 6
	        hcell = new PdfPCell(new Phrase(""));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        table.addCell(hcell);
	        //cell 7
	        hcell = new PdfPCell(new Phrase("No."+invoiceReceiptDetailsObj.getReceiptMaster().getReceiptNo()+invoiceReceiptDetailsObj.getReceiptMaster().getReceipt_seq()+"\nDate."+strDate , new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        hcell.setBorder(0);
	        table.addCell(hcell);
	        //cell 8
	        hcell = new PdfPCell(new Phrase(""));
	        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        hcell.setBorder(0);
	        table.addCell(hcell);
	        
	        pTable.addCell(table);
	         
	        mcell = new PdfPCell(new Phrase("\n\n", headFont));
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
	        hcell = new PdfPCell(new Phrase(""+invoiceReceiptDetailsObj.getBillDetails().getCustomerMaster().getName_of_shop(), 
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
	        String subj=" vide "+invoiceReceiptDetailsObj.getReceiptMaster().getPaymode().toUpperCase()+" and "+invoiceReceiptDetailsObj.getReceiptMaster().getPaymode().toUpperCase()+" No.";
	        if(invoiceReceiptDetailsObj.getReceiptMaster().getPaymode() != null && (invoiceReceiptDetailsObj.getReceiptMaster().getPaymode().equalsIgnoreCase("Cheque") ||
	        		invoiceReceiptDetailsObj.getReceiptMaster().getPaymode().equalsIgnoreCase("RTGS") ||
	        		invoiceReceiptDetailsObj.getReceiptMaster().getPaymode().equalsIgnoreCase("NEFT")) && invoiceReceiptDetailsObj.getReceiptMaster().getRtgs_utr_no()!=null) {
	        	subj=subj+""+invoiceReceiptDetailsObj.getReceiptMaster().getRtgs_utr_no();
	        	subj=subj+" against invoice No."+invoiceReceiptDetailsObj.getBillDetails().getInvoiceNo()+".";
	        }else {
	        	subj=subj+" against invoice No."+invoiceReceiptDetailsObj.getBillDetails().getInvoiceNo()+".";
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
