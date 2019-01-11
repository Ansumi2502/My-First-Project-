/**
 * Created By Anamika Pandey
 */
package com.myapp.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;

import java.util.logging.Level;
import java.util.logging.Logger;



import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfWriter;
import com.myapp.modal.BillDetails;
import com.myapp.modal.ItemDistributionDetails;

import com.myapp.utilities.NumberToWords;

public class GenerateBill{
	public static final String FOOTER = "src/main/resources/static/images/invoice_footer.jpg";
    public static final String HEADER = "src/main/resources/static/images/invoice_header.jpg";
    
    public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
    	
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img, true);
        return cell;
    }
   
    private static PdfPTable generateCopies(BillDetails billDetailsObj,List<ItemDistributionDetails> itemDistributionDetailsColl,int count) {
		try {
			
			PdfPTable pTable = new PdfPTable(1);
			pTable.setWidthPercentage(100);
			pTable.setWidths(new int[]{100});
			DecimalFormat df2 = new DecimalFormat(".##");
	        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA);
	        headFont2.setSize(12);
	        Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC);
	    	
	    	PdfPCell mcell;
	        pTable.addCell(createImageCell(HEADER));
	        
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            String strDate= formatter.format(new Date());
           
            
            PdfPTable tableS = new PdfPTable(3);
            tableS.setWidthPercentage(100);
            tableS.setWidths(new int[]{30,40,30});

            PdfPCell hcell;
            
            if(count==1) {
            	mcell = new PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC,BaseColor.RED)));
		        mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        mcell.setBorder(0);
		        tableS.addCell(mcell);
		        
		        mcell = new PdfPCell(new Phrase("MANSURPUR, Distt. Muzaffarnagar ( U. P.)",new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
		        mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        mcell.setBorder(0);
		        tableS.addCell(mcell);
            	
	        	mcell = new PdfPCell(new Phrase("Recepient Copy",new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC,BaseColor.RED)));
		        mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        mcell.setBorder(0);
		        tableS.addCell(mcell);
	        }else if(count==2) {
	        	mcell = new PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC,BaseColor.RED)));
		        mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        mcell.setBorder(0);
		        tableS.addCell(mcell);
		        
		        mcell = new PdfPCell(new Phrase("MANSURPUR, Distt. Muzaffarnagar ( U. P.)",new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
		        mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        mcell.setBorder(0);
		        tableS.addCell(mcell);
		        
	        	mcell = new PdfPCell(new Phrase("Transporter Copy",new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC,BaseColor.RED)));
		        mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        mcell.setBorder(0);
		        mcell.setColspan(3);
		        tableS.addCell(mcell);
	        }else if(count==3) {
	        	mcell = new PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC,BaseColor.RED)));
		        mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        mcell.setBorder(0);
		        tableS.addCell(mcell);
		        
		        mcell = new PdfPCell(new Phrase("MANSURPUR, Distt. Muzaffarnagar ( U. P.)",new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
		        mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        mcell.setBorder(0);
		        tableS.addCell(mcell);
	        	
	        	mcell = new PdfPCell(new Phrase("Seller Copy",new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC,BaseColor.RED)));
		        mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        mcell.setBorder(0);
		        mcell.setColspan(3);
		        tableS.addCell(mcell);
	        }
            
            mcell = new PdfPCell(new Phrase("CL-2 DEPOT :-        "+billDetailsObj.getDepotMaster().getName(),new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	        mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        mcell.setBorder(0);
	        mcell.setColspan(3);
	        tableS.addCell(mcell);
	        
	        String number="";
			if(billDetailsObj.getInvoice_seq()<10) {
				number="000"+billDetailsObj.getInvoice_seq();
			}else if(billDetailsObj.getInvoice_seq()<100){
				number="00"+billDetailsObj.getInvoice_seq();
			}else if(billDetailsObj.getInvoice_seq()<1000){
				number="0"+billDetailsObj.getInvoice_seq();
			}
	        
	        mcell = new PdfPCell(new Phrase("Invoice No : "+ billDetailsObj.getInvoiceNo()+number ,new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	        mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        mcell.setBorder(0);
	        mcell.setColspan(3);
	        tableS.addCell(mcell);
		       
	    	mcell = new PdfPCell(new Phrase("",new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	        mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        mcell.setBorder(0);
	        mcell.setColspan(3);
	        tableS.addCell(mcell);
            
            hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            tableS.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
            hcell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            hcell.setColspan(2);
            hcell.setBorder(0);
            tableS.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Name Of Licensee :- "+billDetailsObj.getCustomerMaster().getName_of_licence(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            hcell.setColspan(3);
            tableS.addCell(hcell);
           
            
        	hcell = new PdfPCell(new Phrase("Name of Shop :- "+billDetailsObj.getCustomerMaster().getName_of_shop(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setNoWrap(true);
            hcell.setBorder(0);
            hcell.setColspan(3);
            tableS.addCell(hcell);
            
            if(billDetailsObj != null && billDetailsObj.getCustomerMaster() != null && billDetailsObj.getCustomerMaster().getLicence_no_of_shop() != null) {
	            hcell = new PdfPCell(new Phrase("Licence No. :- "+billDetailsObj.getCustomerMaster().getLicence_no_of_shop(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            hcell.setBorder(0);
	            hcell.setColspan(2);
	            tableS.addCell(hcell);
            }else {
            	hcell = new PdfPCell(new Phrase("Licence No. :- ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setBorder(0);
                hcell.setColspan(2);
                tableS.addCell(hcell);
            }
            
            /*hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            tableS.addCell(hcell);*/
            
            if(billDetailsObj != null && billDetailsObj.getCustomerMaster() != null && billDetailsObj.getCustomerMaster().getPan_no_of_licencee() != null) {
	            hcell = new PdfPCell(new Phrase("PAN No. :- "+billDetailsObj.getCustomerMaster().getPan_no_of_licencee(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            hcell.setBorder(0);
	            tableS.addCell(hcell);
            }else {
            	hcell = new PdfPCell(new Phrase("PAN No. :- "+billDetailsObj.getCustomerMaster().getPan_no_of_licencee(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setBorder(0);
                tableS.addCell(hcell);
            }
	        
	        hcell = new PdfPCell(new Phrase("Date :- "+formatter.format(billDetailsObj.getEntry_date()), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            tableS.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            tableS.addCell(hcell);
            
            if(billDetailsObj.getCustomerMaster().getMobileNo() != null) {
	            hcell = new PdfPCell(new Phrase("Contact No. :- "+billDetailsObj.getCustomerMaster().getMobileNo(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
	            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            hcell.setBorder(0);
	            tableS.addCell(hcell);
            }else {
            	hcell = new PdfPCell(new Phrase("Contact No. :- ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setBorder(0);
                tableS.addCell(hcell);
            }
            
            /*if(billDetailsObj != null && billDetailsObj.getGstin() != null) {
	            hcell = new PdfPCell(new Phrase("GSTIN :- "+billDetailsObj.getGstin(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            hcell.setBorder(0);
	            tableS.addCell(hcell);
            }else {
            	hcell = new PdfPCell(new Phrase("GSTIN :- ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            hcell.setBorder(0);
	            tableS.addCell(hcell);
            }*/
            
            /*hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            tableS.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            tableS.addCell(hcell);
            
            if(billDetailsObj != null && billDetailsObj.getCustomerMaster() != null && billDetailsObj.getCustomerMaster().getLicence_no_of_shop() != null) {
	            hcell = new PdfPCell(new Phrase("Licence No. :- "+billDetailsObj.getCustomerMaster().getLicence_no_of_shop(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            hcell.setBorder(0);
	            tableS.addCell(hcell);
            }else {
            	hcell = new PdfPCell(new Phrase("Licence No. :- ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setBorder(0);
                tableS.addCell(hcell);
            }*/
            
           /* if(billDetailsObj != null && billDetailsObj.getDate_s() != null) {
	            hcell = new PdfPCell(new Phrase("Bill Date :- "+ formatter.format(billDetailsObj.getDate_s()), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            hcell.setBorder(0);
	            tableS.addCell(hcell);
            }else {
            	hcell = new PdfPCell(new Phrase("Bill Date :- ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setBorder(0);
                tableS.addCell(hcell);
            }*/
            
            hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            tableS.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            tableS.addCell(hcell);
            
            /*if(billDetailsObj != null && billDetailsObj.getEntry_date() != null) {
	            hcell = new PdfPCell(new Phrase("Invoice Date :- "+formatter.format(billDetailsObj.getEntry_date()), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
	            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            hcell.setBorder(0);
	            tableS.addCell(hcell);
            }else {
            	hcell = new PdfPCell(new Phrase("Invoice Date :- ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setBorder(0);
                tableS.addCell(hcell);
            }*/
            
            hcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(0);
            tableS.addCell(hcell);
            
            pTable.addCell(tableS);
            int count1=0;
            PdfPTable tableBD = new PdfPTable(10);
            tableBD.setWidthPercentage(100);
            tableBD.setWidths(new int[]{8,10,20,10,8,7,13,7,10,7});
            tableBD.setPaddingTop(3f);
            long totRs=0;
            long totPaise=0;
            double totolAmount=0;
            double grandTotAmount=0;
            double temp=0;
            double totalBL=0;
            double totCases=0;
            double totPieces=0;
            for (ItemDistributionDetails itemDistributionDetails : itemDistributionDetailsColl) {
            	if(itemDistributionDetails.getBillDetails().getId() != billDetailsObj.getId()) {
            		continue;
            	}
            	PdfPCell cell;
            	if(count1==0) {
                	cell = new PdfPCell(new Phrase("PARTICULARS", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                	cell.setColspan(2);
                	cell.setRowspan(3);
                	tableBD.addCell(cell);
                    cell = new PdfPCell(new Phrase("Brand", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                	cell.setRowspan(3);
                	tableBD.addCell(cell);
                    /*cell = new PdfPCell(new Phrase("Hologram/Stiker No", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                	cell.setRowspan(3);
                	tableBD.addCell(cell);*/
                    cell = new PdfPCell(new Phrase("BL", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                	cell.setRowspan(3);
                	tableBD.addCell(cell);
                    cell = new PdfPCell(new Phrase("Cases/Pieces", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                	cell.setRowspan(3);
                	cell.setColspan(2);
                	tableBD.addCell(cell);
                    cell = new PdfPCell(new Phrase("Rate Per Cases\nRs.                 P.", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                	cell.setRowspan(3);
                	cell.setColspan(2);
                	tableBD.addCell(cell);
                	cell = new PdfPCell(new Phrase("Amount\nRs.                 P.", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.BLACK)));
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                	cell.setRowspan(3);
                	cell.setColspan(2);
                	tableBD.addCell(cell);
                }
            	count1++;
            	double bl=0;
            	if(itemDistributionDetails.getBrandMaster().getPercentage() != null && itemDistributionDetails.getBrandMaster().getPercentage().equalsIgnoreCase("36")) {
            		bl=itemDistributionDetails.getCases()*9;
            	}else if(itemDistributionDetails.getBrandMaster().getPercentage() != null && itemDistributionDetails.getBrandMaster().getPercentage().equalsIgnoreCase("42.8")) {
            		//42.8/36=1.1888888889;
            		bl=itemDistributionDetails.getCases()*1.1888888889*9;
            	}else if(itemDistributionDetails.getBrandMaster().getPercentage() != null && itemDistributionDetails.getBrandMaster().getPercentage().equalsIgnoreCase("25")) {
            		double a1=25/36;
            		bl=itemDistributionDetails.getCases()*0.6944444444*9;
            	}
            	totalBL=totalBL+Double.parseDouble(df2.format(bl));
            	cell = new PdfPCell(new Phrase(itemDistributionDetails.getBrandMaster().getPercentage()+" % V/V", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setRotation(90);
            	cell.setRowspan(3);
            	tableBD.addCell(cell);
            	cell = new PdfPCell(new Phrase("200 ML", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setRowspan(3);
            	tableBD.addCell(cell);
            	cell = new PdfPCell(new Phrase(itemDistributionDetails.getBrandMaster().getName(), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setRowspan(3);
            	tableBD.addCell(cell);
            	/*cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setRowspan(3);
            	tableBD.addCell(cell);*/
            	cell = new PdfPCell(new Phrase(Double.parseDouble(df2.format(bl))+"", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setRowspan(3);
            	tableBD.addCell(cell);
            	totCases=totCases+itemDistributionDetails.getCases();
            	cell = new PdfPCell(new Phrase(""+itemDistributionDetails.getCases(), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setRowspan(3);
            	tableBD.addCell(cell);
            	totPieces=totPieces+itemDistributionDetails.getPieces();
            	cell = new PdfPCell(new Phrase(""+itemDistributionDetails.getPieces(), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	cell.setRowspan(3);
            	tableBD.addCell(cell);
            	String brandAmt = "" + BigDecimal.valueOf(itemDistributionDetails.getBrandMaster().getAmount()).toPlainString() ;
            	String[] brandAmtTemp =brandAmt.split("\\.");
            	if(brandAmtTemp.length>1 && Long.parseLong(brandAmtTemp[1]) > 0) {
            		if(brandAmtTemp[1].length()>2) {
            			brandAmtTemp[1]=brandAmtTemp[1].substring(0,2);
            		}
            		cell = new PdfPCell(new Phrase(""+brandAmtTemp[0], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	            	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            	cell.setRowspan(3);
	            	tableBD.addCell(cell);
	            	if(brandAmtTemp[1].length()<2) {
		            	cell = new PdfPCell(new Phrase(brandAmtTemp[1]+"0", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
		            	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            	cell.setRowspan(3);
		            	tableBD.addCell(cell);
	            	}else {
	            		cell = new PdfPCell(new Phrase(""+brandAmtTemp[1], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
		            	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            	cell.setRowspan(3);
		            	tableBD.addCell(cell);
	            	}
            	}else {
	            	cell = new PdfPCell(new Phrase(""+brandAmtTemp[0], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	            	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            	cell.setRowspan(3);
	            	tableBD.addCell(cell);
	            	cell = new PdfPCell(new Phrase("00", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	            	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            	cell.setRowspan(3);
	            	tableBD.addCell(cell);
            	}

                String amt = "" + BigDecimal.valueOf(itemDistributionDetails.getAmount()).toPlainString() ;
                totolAmount=totolAmount+itemDistributionDetails.getAmount();
                grandTotAmount=grandTotAmount+itemDistributionDetails.getAmount();
                temp=temp+itemDistributionDetails.getAmount();
                String[] amtTemp =amt.split("\\.");
                if (amtTemp.length>1 && Long.parseLong(amtTemp[1]) > 0) {
                	if(amtTemp[1].length()>2)
                	amtTemp[1]=amtTemp[1].substring(0, 2);
    				cell = new PdfPCell(new Phrase(""+amtTemp[0], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
                	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                	cell.setRowspan(3);
                	tableBD.addCell(cell);
                	if(amtTemp[1].length()<2) {
                		cell = new PdfPCell(new Phrase(amtTemp[1]+"0", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	                	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                	cell.setRowspan(3);
	                	tableBD.addCell(cell);
                	}else {
	                	cell = new PdfPCell(new Phrase(""+amtTemp[1], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	                	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                	cell.setRowspan(3);
	                	tableBD.addCell(cell);
                	}
                	totRs=totRs+Long.parseLong(amtTemp[0]);
                	totPaise=totPaise+Long.parseLong(amtTemp[1]);
    			} else {
    				cell = new PdfPCell(new Phrase(""+amtTemp[0], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
                	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                	cell.setRowspan(3);
                	tableBD.addCell(cell);
                	cell = new PdfPCell(new Phrase("00", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
                	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                	cell.setRowspan(3);
                	tableBD.addCell(cell);
                	totRs=totRs+Long.parseLong(amtTemp[0]);
    			}
            	
            }
            int itemp=7;
            itemp=itemp-count1;
            for(int aa=0;aa<itemp;aa++) {
            	mcell = new PdfPCell(new Phrase("25 % V/V", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
                mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                mcell.setRotation(90);
                mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("200 ML", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
                mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("3", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
            	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("4", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
            	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("5", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
            	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("6", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
            	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("7", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
            	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("8", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
            	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("9", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
            	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("10", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
            	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            }
            mcell = new PdfPCell(new Phrase("1", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
            mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            mcell.setRowspan(2);
            mcell.setColspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("3", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("4", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("5", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("6", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("7", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("8", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("9", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("10", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,BaseColor.WHITE)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	
            mcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            mcell.setRowspan(2);
            mcell.setColspan(2);
            mcell.setBorder(Rectangle.NO_BORDER);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("Grand Total", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	mcell.setRowspan(2);
        	mcell.setBorder(Rectangle.NO_BORDER);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase(""+df2.format(totalBL), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase(""+totCases, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase(""+totPieces, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
        	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("Total Amount", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
        	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	 mcell.setColspan(2);
        	 mcell.setRowspan(2);
        	 mcell.setBorder(Rectangle.NO_BORDER);
        	tableBD.addCell(mcell);
        	
        	
        	String tempTotAmt = "" + BigDecimal.valueOf(totolAmount).toPlainString() ;
        	String[] tempTotAmtArr =tempTotAmt.split("\\.");
        	
        	if (tempTotAmtArr.length>1 && Long.parseLong(tempTotAmtArr[1]) > 0) {
            	if(tempTotAmtArr[1].length()>2)
            		tempTotAmtArr[1]=tempTotAmtArr[1].substring(0, 2);
	            	mcell = new PdfPCell(new Phrase(""+tempTotAmtArr[0], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	            	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            	mcell.setRowspan(2);
	            	tableBD.addCell(mcell);
	            	if(tempTotAmtArr[1].length()<2) {
	            		mcell = new PdfPCell(new Phrase(tempTotAmtArr[1]+"0", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	                	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                	mcell.setRowspan(2);
	                	tableBD.addCell(mcell);
	            	}else {
	            		mcell = new PdfPCell(new Phrase(tempTotAmtArr[1]+"", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	                	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                	mcell.setRowspan(2);
	                	tableBD.addCell(mcell);
	            	}
			} else {
				mcell = new PdfPCell(new Phrase(""+tempTotAmtArr[0], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("00", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
			}
        	mcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            mcell.setColspan(6);
            mcell.setRowspan(2);
            mcell.setBorder(Rectangle.NO_BORDER);
            tableBD.addCell(mcell);
            
        	mcell = new PdfPCell(new Phrase("TCS(1%)", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
        	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	mcell.setColspan(2);
        	mcell.setBorder(Rectangle.NO_BORDER);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	double tcstotAmount=totolAmount*(0.01);
        	grandTotAmount=grandTotAmount+tcstotAmount;
        	temp=temp+tcstotAmount;
        	String tcsAmount = "" + BigDecimal.valueOf(tcstotAmount).toPlainString() ;
            String[] tcsAmountTemp =tcsAmount.split("\\.");
            if(tcsAmountTemp[1] != null &&  tcsAmountTemp[1].length()>2) {
            	tcsAmountTemp[1]=tcsAmountTemp[1].substring(0,2);
            }
            if (tcsAmountTemp.length>1 && Long.parseLong(tcsAmountTemp[1]) > 0) {
            	mcell = new PdfPCell(new Phrase(""+tcsAmountTemp[0], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	
            	if(tcsAmountTemp[1].length()==1) {
                	mcell = new PdfPCell(new Phrase(tcsAmountTemp[1]+"0", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
                	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                	mcell.setRowspan(2);
                	tableBD.addCell(mcell);
            	}else {
            		mcell = new PdfPCell(new Phrase(""+tcsAmountTemp[1], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
                	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                	mcell.setRowspan(2);
                	tableBD.addCell(mcell);
            	}
            	
            	
			} else {
				mcell = new PdfPCell(new Phrase(""+tcsAmountTemp[0], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
				mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("00", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
			}
        	
            mcell = new PdfPCell(new Phrase("Batch No :-", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            mcell.setRowspan(2);
            mcell.setColspan(2);
            mcell.setBorder(Rectangle.NO_BORDER);
            tableBD.addCell(mcell);
            if(billDetailsObj != null && billDetailsObj.getBatch_no() != null) {
	            mcell = new PdfPCell(new Phrase(""+billDetailsObj.getBatch_no(), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	            mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            mcell.setRowspan(2);
	            mcell.setColspan(4);
	            mcell.setBorder(Rectangle.NO_BORDER);
	            tableBD.addCell(mcell);
            }else{
            	mcell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	            mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            mcell.setRowspan(2);
	            mcell.setColspan(4);
	            mcell.setBorder(Rectangle.NO_BORDER);
	            tableBD.addCell(mcell);
            }
        	mcell = new PdfPCell(new Phrase("Total", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
        	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	mcell.setRowspan(2);
        	mcell.setColspan(2);
        	mcell.setBorder(Rectangle.NO_BORDER);
        	tableBD.addCell(mcell);
        	String grRs = "" + BigDecimal.valueOf(temp).toPlainString() ;
            String[] grRsTemp =grRs.split("\\.");
            if (grRsTemp.length>1 && Long.parseLong(grRsTemp[1]) > 0) {
            	if(grRsTemp[1].length()>1)
        		grRsTemp[1]=grRsTemp[1].substring(0, 2);
            	mcell = new PdfPCell(new Phrase(""+grRsTemp[0], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	mcell.setRowspan(2);
            	tableBD.addCell(mcell);
            	
            	
            	if(grRsTemp[1].length()==1) {
                	mcell = new PdfPCell(new Phrase(grRsTemp[1]+"0", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
                	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                	mcell.setRowspan(2);
                	tableBD.addCell(mcell);
            	}else {
            		mcell = new PdfPCell(new Phrase(""+grRsTemp[1], new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
                	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                	mcell.setRowspan(2);
                	tableBD.addCell(mcell);
            	}
            	
			} else {
				mcell = new PdfPCell(new Phrase(grRsTemp[0]+"", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        	mcell.setRowspan(2);
	        	tableBD.addCell(mcell);
	        	mcell = new PdfPCell(new Phrase("00", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
	        	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        	mcell.setRowspan(2);
	        	tableBD.addCell(mcell);
			}
        	
        	mcell = new PdfPCell(new Phrase("Truck / Tampo No :-", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            mcell.setRowspan(2);
            mcell.setColspan(2);
            mcell.setBorder(Rectangle.NO_BORDER);
            tableBD.addCell(mcell);
            if(billDetailsObj.getTruck_temp_no() != null) {
                mcell = new PdfPCell(new Phrase(""+billDetailsObj.getTruck_temp_no(), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
            	mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	mcell.setBorder(Rectangle.NO_BORDER);
            	mcell.setRowspan(2);
            	mcell.setColspan(4);
            	tableBD.addCell(mcell);
            }else {
            	mcell = new PdfPCell(new Phrase("........................................", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
             	mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
             	mcell.setBorder(Rectangle.NO_BORDER);
             	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
             	mcell.setRowspan(2);
             	mcell.setColspan(4);
             	tableBD.addCell(mcell);
            }
        	mcell = new PdfPCell(new Phrase("Grand Total (Round Off)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
        	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	mcell.setColspan(2);
        	mcell.setNoWrap(true);
        	mcell.setRowspan(2);
        	mcell.setBorder(Rectangle.NO_BORDER);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase(Math.round(grandTotAmount)+"", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL,BaseColor.BLACK)));
        	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	mcell = new PdfPCell(new Phrase("00", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL,BaseColor.BLACK)));
        	mcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	mcell.setRowspan(2);
        	tableBD.addCell(mcell);
        	
        	String totAmt1 = "" + BigDecimal.valueOf(Math.round(grandTotAmount)).toPlainString() ;
            String[] amtArr =totAmt1.split("\\.");
            String totAmtWords="";
            if (amtArr.length>1 && Long.parseLong(amtArr[1]) > 0) {
				totAmtWords = NumberToWords.convertNumToWord((long) Double
						.parseDouble(amtArr[0]))
						+ " and "
						+ NumberToWords.convertNumToWord((long) Double
								.parseDouble(amtArr[1])) + " Paise";
				totAmtWords=totAmtWords+" Only";
			} else {
				totAmtWords = NumberToWords.convertNumToWord((long) Double
						.parseDouble(amtArr[0]));
				totAmtWords=totAmtWords+" Only";
			}
        	
        	if(totAmtWords.length()>0) {
        		mcell = new PdfPCell(new Phrase("\n" ,new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            	mcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	mcell.setColspan(10);
            	mcell.setRowspan(2);
            	mcell.setBorder(Rectangle.NO_BORDER);
            	tableBD.addCell(mcell);
        		mcell = new PdfPCell(new Phrase("Rs in words:", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            	mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	mcell.setBorder(1);
            	mcell.setColspan(2);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase(""+totAmtWords, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC,BaseColor.BLACK)));
            	mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	mcell.setColspan(8);
            	mcell.setBorder(1);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("\n" ,new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            	mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	mcell.setColspan(10);
            	mcell.setRowspan(2);
            	mcell.setBorder(Rectangle.NO_BORDER);
            	tableBD.addCell(mcell);
        	}else {
        		mcell = new PdfPCell(new Phrase("\n" ,new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            	mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	mcell.setColspan(10);
            	mcell.setRowspan(2);
            	mcell.setBorder(Rectangle.NO_BORDER);
            	tableBD.addCell(mcell);
        		mcell = new PdfPCell(new Phrase("Rs in words................................................................................ ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            	mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	mcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	mcell.setColspan(10);
            	mcell.setBorder(1);
            	tableBD.addCell(mcell);
            	mcell = new PdfPCell(new Phrase("\n" ,new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            	mcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	mcell.setColspan(10);
            	mcell.setRowspan(2);
            	mcell.setBorder(Rectangle.NO_BORDER);
            	tableBD.addCell(mcell);
        	}
            pTable.addCell(tableBD);
            pTable.addCell(createImageCell(FOOTER));
	        return pTable;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ByteArrayInputStream generateBill(BillDetails billDetailsObj,List<ItemDistributionDetails> itemDistributionDetailsColl,String headerName,String subHeaderName,String userType) {
        Document document = new Document(PageSize.A4, 36, 36, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            // add header and footer
            //HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            //writer.setPageEvent(event);
            // write to document
            document.open();
            document.add(generateCopies(billDetailsObj,itemDistributionDetailsColl,1));
            if(userType != null && userType.equals("USER")) {
	            document.newPage();
	            document.add(generateCopies(billDetailsObj,itemDistributionDetailsColl,2));
	            document.newPage();
	            document.add(generateCopies(billDetailsObj,itemDistributionDetailsColl,3));
            }
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(GenerateBill.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
	
}
