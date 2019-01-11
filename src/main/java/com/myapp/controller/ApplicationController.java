/**
 * Created By Anamika Pandey
 */
package com.myapp.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.DateUtils;

import com.itextpdf.text.Image;
import com.myapp.modal.ApplnUser;
import com.myapp.modal.BillDetails;
import com.myapp.modal.BrandMaster;
import com.myapp.modal.CustomerMaster;
import com.myapp.modal.DepotMaster;
import com.myapp.modal.InvoiceReceiptDetails;
import com.myapp.modal.ItemDistributionDetails;
import com.myapp.modal.KeyMaster;
import com.myapp.modal.ReceiptMaster;
import com.myapp.reports.GenerateBill;
import com.myapp.reports.PrintBulkReceipt;
import com.myapp.reports.PrintReceipt;
import com.myapp.service.ApplnUserService;
import com.myapp.service.BillDetailsService;
import com.myapp.service.BrandService;
import com.myapp.service.CustomerService;
import com.myapp.service.DepotService;
import com.myapp.service.InvoiceReceiptService;
import com.myapp.service.ItemDistributionDetailsService;
import com.myapp.service.KeyMasterService;
import com.myapp.service.ReceiptService;
import com.myapp.utilities.PasswordEncryptionDecryption;
import com.myapp.utilities.ReceiptModal;
import com.myapp.utilities.SelectionOption;
import com.myapp.wrapperObjects.ItemDistributionsCreationDto;

@Controller
public class ApplicationController{
	@Autowired
	private ApplnUserService applnUserService;
	@Autowired
	private DepotService depotService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private BillDetailsService billDetailsService;
	@Autowired
	private ItemDistributionDetailsService itemDistributionDetailsService;
	@Autowired
	private ReceiptService receiptService;
	@Autowired
	private InvoiceReceiptService invoiceReceiptService;
	@Autowired
	private KeyMasterService keyMasterService;
	
	private static String userType="";
	
	private static String headerName="";
	private static String subHeaderName="";
	private static String appName="";
	
	private long[] receiptIds;
	
	public void loadKeys() {
		headerName=keyMasterService.findByName("headerName").getValue();
		subHeaderName=keyMasterService.findByName("subHeaderName").getValue();
		appName=keyMasterService.findByName("appName").getValue();
	}
	
	@RequestMapping("/welcomepage")
	public String Welcome(HttpServletRequest request) {
		ApplnUser applnUser=getApplnUserDetails();
		request.setAttribute("userType",userType);
		loadKeys();
		request.setAttribute("appName",appName);
		return "welcomepage";
	}
	
	@GetMapping("/register")
	public String userRegistration(HttpServletRequest request) {
		String[] ALL_STATUS = new String[] {
				"ACTIVE","INACTIVE"};
		
		Collection allStatusColl=getSimpleOptionCollection(ALL_STATUS);
		Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			String[] ALL_USER_TYPE= new String[] {"USER"};
			Collection allUsersTypeColl=getSimpleOptionCollection(ALL_USER_TYPE);
			request.setAttribute("userTypeList", allUsersTypeColl);
			depotColls.add(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			String[] ALL_USER_TYPE= new String[] {"ADMIN","USER"};
			Collection allUsersTypeColl=getSimpleOptionCollection(ALL_USER_TYPE);
			request.setAttribute("userTypeList", allUsersTypeColl);
			depotColls=depotService.showAllDepot();
		}
		request.setAttribute("depotList", depotColls);
		request.setAttribute("statusList", allStatusColl);
		request.setAttribute("userType",userType);
		loadKeys();
		request.setAttribute("appName",appName);
		return "register";
	}
	
	@PostMapping("/saveUser")
	public String registerApplnUser(@ModelAttribute ApplnUser applnUser,
			BindingResult bindingResult,HttpServletRequest request) {
		
		ApplnUser applnUserTempObj=applnUserService.findByUserName(applnUser.getUserId());
		if(applnUserTempObj != null) {
			request.setAttribute("error", "User id is already registered. Please use different user id");
			return "register";
		}
		applnUser.setPassword(PasswordEncryptionDecryption.encodePassword(applnUser.getPassword()));
		applnUserService.saveAppnUser(applnUser);
		request.setAttribute("success", "You have registered successfully");
		ApplnUser applnUser1=getApplnUserDetails();
		String[] ALL_STATUS = new String[] {
				"ACTIVE","INACTIVE"};
		
		Collection allStatusColl=getSimpleOptionCollection(ALL_STATUS);
		Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		if(userType != null && userType.equals("USER")) {
			String[] ALL_USER_TYPE= new String[] {"USER"};
			Collection allUsersTypeColl=getSimpleOptionCollection(ALL_USER_TYPE);
			request.setAttribute("userTypeList", allUsersTypeColl);
			depotColls.add(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			String[] ALL_USER_TYPE= new String[] {"ADMIN","USER"};
			Collection allUsersTypeColl=getSimpleOptionCollection(ALL_USER_TYPE);
			request.setAttribute("userTypeList", allUsersTypeColl);
			depotColls=depotService.showAllDepot();
		}
		request.setAttribute("userType",userType);
		request.setAttribute("depotList", depotColls);
		request.setAttribute("statusList", allStatusColl);
		loadKeys();
		request.setAttribute("appName",appName);
		return "register";
	}
	
	@RequestMapping("/show-users")
	public String showApplnUsers(HttpServletRequest request) {
		List<ApplnUser> applnUserList=new ArrayList<ApplnUser>();
		ApplnUser applnUser=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			applnUserList.addAll(applnUserService.showAllApplnUsersDepotWise(applnUser.getDepotMaster()));
		}else if(userType != null && userType.equals("ADMIN")) {
			applnUserList.addAll(applnUserService.showAllApplnUsers());
		}
		request.setAttribute("applnUsers", applnUserList);
		ApplnUser applnUser1=getApplnUserDetails();
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		loadKeys();
		return "usersList";
	}
	
	@RequestMapping(value="/delete-user/{id}", method = RequestMethod.GET)
	public String deleteApplnUser(@PathVariable("id") Long id,
			HttpServletRequest request) {
		applnUserService.deleteApplnUser(id);
		List<ApplnUser> applnUserList=new ArrayList<ApplnUser>();
		ApplnUser applnUser=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			applnUserList.addAll(applnUserService.showAllApplnUsersDepotWise(applnUser.getDepotMaster()));
		}else if(userType != null && userType.equals("ADMIN")) {
			applnUserList.addAll(applnUserService.showAllApplnUsers());
		}
		request.setAttribute("applnUsers", applnUserList);
		request.setAttribute("success", "User Details deleted successfully");
		ApplnUser applnUser1=getApplnUserDetails();
		request.setAttribute("userType",userType);
		loadKeys();
		request.setAttribute("appName",appName);
		return "usersList";
	}
	
	@RequestMapping(value= "/edit-user/{id}", method = RequestMethod.GET)
	public String editApplnUser(@PathVariable("id") Long id, ModelMap model ) {
	    model.put("applnUser", applnUserService.editApplnUser(id));
		List<DepotMaster> depotList=new ArrayList<DepotMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			depotList.add(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotList=depotService.showAllDepot();
		}
		model.addAttribute("depotList", depotList);
		ApplnUser applnUser1=getApplnUserDetails();
		model.addAttribute("userType",userType);
		model.addAttribute("appName",appName);
		loadKeys();
	    return  "updateUser";
	}
	
	@RequestMapping("/login-user")
	public String loginApplnUser(@ModelAttribute ApplnUser applnUser,
			HttpServletRequest request) {
		if(applnUserService.findByUserNameAndPassword(applnUser.getUserId(), PasswordEncryptionDecryption.encodePassword(applnUser.getPassword())) != null) {
			request.setAttribute("success", "You have login successfully");
			Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
			Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
			Collection<BrandMaster> brandColls=new ArrayList<BrandMaster>();
			
			ApplnUser applnUser2=getApplnUserDetails();
			loadKeys();
			if(userType != null && userType.equals("USER")) {
				depotColls.add(applnUser2.getDepotMaster());
				customerColls=customerService.showAllCustomerByDepotWise(applnUser2.getDepotMaster());
			}else if(userType != null && userType.equals("ADMIN")) {
				depotColls=depotService.showAllDepot();
				customerColls=customerService.showAllCustomer();
			}
			
			brandColls=brandService.showAllBrand();
			Collections.sort((List)brandColls ,new ComparatorAsc());
			request.setAttribute("billDate", "");
			request.setAttribute("invoiceDate", "");
			request.setAttribute("depotList", depotColls);
			request.setAttribute("customerList", customerColls);
			request.setAttribute("brandList", brandColls);
			request.setAttribute("applnUserName", applnUser.getUserId());
			request.setAttribute("userType",userType);
			request.setAttribute("appName",appName);
			return "homepage";
		}else {
			request.setAttribute("error", "Invalid User Id and Password");
			ApplnUser applnUser1=getApplnUserDetails();
			request.setAttribute("userType",userType);
			request.setAttribute("appName",appName);
			return "welcomepage";
		}
		
	}
	
	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		 ModelAndView mav = new ModelAndView();
	     mav.setViewName("custom-login");
	     loadKeys();
	     request.setAttribute("appName",appName);
	     return mav;
	}
	
	@GetMapping("error")
	public ModelAndView error(HttpServletRequest request) {
	    ModelAndView mav = new ModelAndView();
	    String errorMessage= "You are not authorized for the requested data.";
	    mav.addObject("errorMsg", errorMessage);
	    mav.setViewName("error");
	    ApplnUser applnUser1=getApplnUserDetails();
		mav.addObject("userType",userType);
		loadKeys();
		request.setAttribute("appName",appName);
	    return mav;
    }
	
	@GetMapping("secure/homepage")
	@RequestMapping(value = { "/secure/homepage" }, method = RequestMethod.GET)
	public String homepage(Model model,HttpServletRequest request) {
	    Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
		Collection<BrandMaster> brandColls=new ArrayList<BrandMaster>();
		Collection<BillDetails> billDetailsColls=new ArrayList<BillDetails>();
		
		ApplnUser applnUser2=getApplnUserDetails();
		loadKeys();
		String invoiceNumber="";
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser2.getDepotMaster());
			customerColls=customerService.showAllCustomerByDepotWise(applnUser2.getDepotMaster());
			billDetailsColls=billDetailsService.showAllBillDetailsDepotWise(applnUser2.getDepotMaster());
			invoiceNumber=applnUser2.getDepotMaster().getCode().substring(0, 3);
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
			customerColls=customerService.showAllCustomer();
			billDetailsColls=billDetailsService.showAllBillDetails();
		}

		brandColls=brandService.showAllBrand();
		Collections.sort((List)brandColls ,new ComparatorAsc());
		
		model.addAttribute("depotList", depotColls);
		model.addAttribute("invoiceNumber", invoiceNumber);
		model.addAttribute("customerList", customerColls);
		model.addAttribute("brandList", brandColls);
		model.addAttribute("billDetailsList", billDetailsColls);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("loggedInApplnUserId", auth.getPrincipal());
		model.addAttribute("userType",userType);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		request.setAttribute("currentDate",formatter.format(new Date()));
		request.setAttribute("appName",appName);
	    return "homepage";
    }
	
	private class ComparatorAsc implements Comparator{
		public int compare(Object arg0, Object arg1) {
		BrandMaster ar1 = (BrandMaster)arg0;
		BrandMaster ar2 = (BrandMaster)arg1;
		String perc1 = new String(ar1.getPercentage());
		String perc2 =new String(ar2.getPercentage());

		return perc1.compareTo(perc2);
		}
	}
	private class ComparatorInvoiceRecect implements Comparator{
		public int compare(Object arg0, Object arg1) {
		InvoiceReceiptDetails ar1 = (InvoiceReceiptDetails)arg0;
		InvoiceReceiptDetails ar2 = (InvoiceReceiptDetails)arg1;
		return (new Long(ar2.getReceiptMaster().getId())
				.compareTo(new Long(ar1.getReceiptMaster().getId())));
		}
	}
	
	@PostMapping("/updateUser")
	public String updateApplnUser(@ModelAttribute("applnUser") ApplnUser applnUser,
			BindingResult bindingResult,HttpServletRequest request,@RequestParam("passwordTemp") String  passwordTemp) {
		ApplnUser applnUserTempObj=applnUserService.findByUserName(applnUser.getUserId());
		loadKeys();
		if (bindingResult.hasErrors()) {
	        return "error";
	    }
		if(applnUserTempObj == null) {
			request.setAttribute("error", "User id is not registered.");
			return "usersList";
		}
		if(passwordTemp.length()>0) {
			applnUser.setPassword(PasswordEncryptionDecryption.encodePassword(passwordTemp));
		}
		applnUserService.saveAppnUser(applnUser);
		request.setAttribute("success", "User Details updated successfully");
		
		List<ApplnUser> applnUserList=new ArrayList<ApplnUser>();
		ApplnUser applnUser2=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			applnUserList.addAll(applnUserService.showAllApplnUsersDepotWise(applnUser2.getDepotMaster()));
		}else if(userType != null && userType.equals("ADMIN")) {
			applnUserList.addAll(applnUserService.showAllApplnUsers());
		}
		request.setAttribute("applnUsers", applnUserList);
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "usersList";
	}
	
	@GetMapping("/addBrand")
	public String addBrand(HttpServletRequest request) {
		Collection<BrandMaster> brandColls=new ArrayList<BrandMaster>();
		brandColls=brandService.showAllBrand();
		Collections.sort((List)brandColls ,new ComparatorAsc());
		request.setAttribute("brandMasters", brandColls);
		request.setAttribute("userType",userType);
		loadKeys();
		request.setAttribute("appName",appName);
		return "brandMaster";
	}
	
	@PostMapping("/saveBrand")
	public String saveBrand(@ModelAttribute BrandMaster brandMaster,
			BindingResult bindingResult,HttpServletRequest request) {
		try {
			brandService.saveBrand(brandMaster);
			loadKeys();
			Collection<BrandMaster> brandColls=new ArrayList<BrandMaster>();
			brandColls=brandService.showAllBrand();
			Collections.sort((List)brandColls ,new ComparatorAsc());
			request.setAttribute("brandMasters", brandColls);
			request.setAttribute("success", "Brand details saved successfully");
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Brand details are not saved.");
		}
		ApplnUser applnUser1=getApplnUserDetails();
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "brandMaster";
	}
	
	@GetMapping("/addDepot")
	public String addDepot(HttpServletRequest request) {
		Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		loadKeys();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
		}
		request.setAttribute("allDepots", depotColls);
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "depoMaster";
	}
	
	@RequestMapping("/addDepoMaster")
	public String addDepoMaster(HttpServletRequest request) {
		Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
		}
		loadKeys();
		request.setAttribute("allDepots", depotColls);
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "addDepoMaster";
	}
	
	@PostMapping("/saveDepot")
	
	public String saveDepot(@ModelAttribute DepotMaster depotMaster,
			BindingResult bindingResult,HttpServletRequest request) {
		try {
			depotService.saveDepot(depotMaster);
			Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
			loadKeys();
			ApplnUser applnUser=getApplnUserDetails();
			if(userType != null && userType.equals("USER")) {
				depotColls.add(applnUser.getDepotMaster());
			}else if(userType != null && userType.equals("ADMIN")) {
				depotColls=depotService.showAllDepot();
			}
			request.setAttribute("allDepots", depotColls);
			request.setAttribute("success", "Depot details saved successfully");
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Depot details are not saved.");
		}
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "depoMaster";
	}
	
	@RequestMapping(value = { "/addCustomer" }, method = RequestMethod.GET)
	public String addCustomer(Model model,HttpServletRequest request) {
		
		Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		loadKeys();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser.getDepotMaster());
			customerColls=customerService.showAllCustomerByDepotWise(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
			customerColls=customerService.showAllCustomer();	
		}
		request.setAttribute("allCustomers", customerColls);
		request.setAttribute("depotList", depotColls);
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "customerMaster";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute CustomerMaster customerMaster,
			BindingResult bindingResult,HttpServletRequest request,Model model) {
		try {
			customerService.saveCustomer(customerMaster);
			loadKeys();
			request.setAttribute("success", "Customer details saved successfully");
			Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
			Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
			ApplnUser applnUser=getApplnUserDetails();
			if(userType != null && userType.equals("USER")) {
				depotColls.add(applnUser.getDepotMaster());
				customerColls=customerService.showAllCustomerByDepotWise(applnUser.getDepotMaster());
			}else if(userType != null && userType.equals("ADMIN")) {
				depotColls=depotService.showAllDepot();
				customerColls=customerService.showAllCustomer();
			}
			request.setAttribute("depotList", depotColls);
			request.setAttribute("allCustomers", customerColls);
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Customer details are not saved.");
		}
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "customerMaster";
	}
	
	@PostMapping("/saveBillEntry")
	public String saveBillEntry(@ModelAttribute BillDetails billDetails,HttpServletRequest request,Model model,
			@ModelAttribute ItemDistributionsCreationDto itemDistributionsCreationDetails) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			loadKeys();
			model.addAttribute("loggedInApplnUserId", auth.getPrincipal());
			ApplnUser applnUser=applnUserService.findByUserName(auth.getName());
			billDetails.setApplnUser(applnUser);
			/*if(billDetails.getInvoiceNo().length()<1) {
				//Generate automatic invoice number
				Random r = new Random(); 
				int i1 = r.nextInt(9);
				SimpleDateFormat formatter = new SimpleDateFormat("yyddMMhhmmss");
				String txnRefNumber=formatter.format(new Date())+i1;
				if(txnRefNumber != null){
					billDetails.setInvoiceNo("I"+txnRefNumber);
				}
			}else if(billDetails.getInvoiceNo().length()==3) {
				//Generate automatic invoice number
				Random r = new Random(); 
				int i1 = r.nextInt(9);
				SimpleDateFormat formatter = new SimpleDateFormat("yyddMMhhmmss");
				String txnRefNumber=formatter.format(new Date())+i1;
				if(txnRefNumber != null){
					billDetails.setInvoiceNo(billDetails.getInvoiceNo()+txnRefNumber);
				}
			}*/
			ApplnUser applnUser2=getApplnUserDetails();
			SimpleDateFormat f = new SimpleDateFormat("MMM");
			Calendar now = Calendar.getInstance();
			
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date dates=formatter.parse((String)request.getParameter("date_s1"));
			Date entryDate=formatter.parse((String)request.getParameter("entry_date1"));
			
			String receiptNumber="SVP/"+now.get(Calendar.YEAR);
			now.add(Calendar.YEAR, 1);
			receiptNumber=receiptNumber+"-"+now.get(Calendar.YEAR)+"/"+
					applnUser2.getDepotMaster().getCode().substring(0, 3)+"/"+(f.format(entryDate)).toUpperCase()+"/I/";
			String checkDepo="/"+applnUser2.getDepotMaster().getCode().substring(0, 3)+"/";
			String checkMonth="/"+(f.format(entryDate)).toUpperCase()+"/I/";
			if(receiptNumber != null){
				billDetails.setInvoiceNo(receiptNumber);
			}
			//long len=billDetailsService.getMaxInvoiceSeq()+1;
			Collection lengthOfMaxInvoiceSeq=billDetailsService.showAllBillDetailsDepotWise(billDetails.getDepotMaster());
			long counter=0;
			for(Iterator<BillDetails> itxL=lengthOfMaxInvoiceSeq.iterator();itxL.hasNext();) {
				BillDetails billDe=(BillDetails)itxL.next();
				if(billDe.getInvoiceNo() != null && billDe.getInvoiceNo().contains(checkDepo) &&
						billDe.getInvoiceNo().contains(checkMonth)) {
					counter=counter+1;
				}
			}
			long len=counter+1;
			billDetails.setInvoice_seq(len);
			
			billDetails.setDate_s(dates);
			billDetails.setEntry_date(entryDate);
			billDetails.setStatus("Active");
			billDetailsService.saveBillDetails(billDetails);
			
			for(ItemDistributionDetails itemDistributionsObj:itemDistributionsCreationDetails.getItemDistributionDetails()) {
				if(itemDistributionsObj.getAmount()>0) {
					itemDistributionsObj.setBillDetails(billDetails);
					itemDistributionsObj.setBrandMaster(itemDistributionsObj.getBrandMaster());
					itemDistributionsObj.setAmount(itemDistributionsObj.getAmount());
					itemDistributionDetailsService.saveItemDistributionDetails(itemDistributionsObj); 
				}
			}
			Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
			Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
			Collection<BrandMaster> brandColls=new ArrayList<BrandMaster>();
			Collection<BillDetails> billDetailsColls=new ArrayList<BillDetails>();
			
			if(userType != null && userType.equals("USER")) {
				depotColls.add(applnUser2.getDepotMaster());
				customerColls=customerService.showAllCustomerByDepotWise(applnUser2.getDepotMaster());
				billDetailsColls=billDetailsService.showAllBillDetailsDepotWise(applnUser2.getDepotMaster());
			}else if(userType != null && userType.equals("ADMIN")) {
				depotColls=depotService.showAllDepot();
				customerColls=customerService.showAllCustomer();
				billDetailsColls=billDetailsService.showAllBillDetails();
			}
			brandColls=brandService.showAllBrand();
			Collections.sort((List)brandColls ,new ComparatorAsc());
			model.addAttribute("depotList", depotColls);
			model.addAttribute("customerList", customerColls);
			model.addAttribute("brandList", brandColls);
			model.addAttribute("billDetailsList", billDetailsColls);
			request.setAttribute("success", "Data saved successfully");
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Bill details are not saved.");
		}
		
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "homepage";
	}
	
	public static Collection getSimpleOptionCollection(String[] aValueList) {
		if (aValueList == null) {
			return Collections.EMPTY_LIST;
		}
		SelectionOption option = null;
		Collection optionList = new ArrayList();
		for (int i = 0; i < aValueList.length; i++) {
			option = new SelectionOption();
			String value = aValueList[i];
			option.setText(value);
			option.setDesc(value);
			option.setValue(value);
			optionList.add(option);
		}
		return optionList;
	}
	
    @RequestMapping(value = "/download-billDetail/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateBillReport(@PathVariable("id") Long id) throws IOException {
    	try {
    		loadKeys();
    		ApplnUser applnUser2=getApplnUserDetails();
	        BillDetails billDetails=billDetailsService.getBillDetails(id);
	        List<ItemDistributionDetails> itemDistributionDetailsColl=new ArrayList<ItemDistributionDetails>();
	        if(billDetails != null) {
	        	itemDistributionDetailsColl=itemDistributionDetailsService.showAllItemDistributionDetails();
	        }
	        Collections.sort(itemDistributionDetailsColl, new SortItemsPercentageWise());
	        ByteArrayInputStream bis = GenerateBill.generateBill(billDetails,itemDistributionDetailsColl,headerName,subHeaderName,userType);
	
	        HttpHeaders headers = new HttpHeaders();
	        String fileName=billDetails.getInvoiceNo()+billDetails.getInvoice_seq()+".pdf";
	        headers.add("Content-Disposition", "inline; filename="+fileName);
	
	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public class SortItemsPercentageWise implements Comparator<ItemDistributionDetails> {
        @Override
        public int compare(ItemDistributionDetails item1, ItemDistributionDetails item2) {
            return item1.getBrandMaster().getPercentage().compareTo(item2.getBrandMaster().getPercentage());
        }
    }
    
    @RequestMapping(value = "/print-billDetail-receipt/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> printReceipt(@PathVariable("id") Long id) throws IOException {
    	try {
    		loadKeys();
	        Collection<InvoiceReceiptDetails> invoiceReceiptDetailsColls=new ArrayList<InvoiceReceiptDetails>();
	        invoiceReceiptDetailsColls=invoiceReceiptService.getAllInvoiceReceiptDetails(id);
	        
	        long receiptId=0;
			String invoiceNumber="";
			
			ReceiptModal receiptModal= new ReceiptModal();
	        for(Iterator itx=invoiceReceiptDetailsColls.iterator();itx.hasNext();) {
	        	InvoiceReceiptDetails invoiceReceiptDetails=(InvoiceReceiptDetails)itx.next();
	        
	        	if(invoiceReceiptDetails != null && invoiceReceiptDetails.getReceiptMaster().getId()!=receiptId) {
	        		receiptId=invoiceReceiptDetails.getReceiptMaster().getId();
		        	invoiceNumber=invoiceReceiptDetails.getBillDetails().getInvoiceNo()+
		        			generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getBillDetails().getInvoice_seq());
		        	receiptModal.setAmount(invoiceReceiptDetails.getReceiptMaster().getAmount());
		        	receiptModal.setCustomerName(invoiceReceiptDetails.getBillDetails().getCustomerMaster().getName_of_shop());
		        	receiptModal.setDepotName(invoiceReceiptDetails.getBillDetails().getDepotMaster().getName());
		        	receiptModal.setPaymentMode(invoiceReceiptDetails.getReceiptMaster().getPaymode());
		        	receiptModal.setReceiptDate(invoiceReceiptDetails.getReceiptMaster().getReceiptDate());
		        	receiptModal.setReceiptNumber(invoiceReceiptDetails.getReceiptMaster().getReceiptNo()
		        			+generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getReceiptMaster().getReceipt_seq()));
		        	receiptModal.setReceiptMasterId(invoiceReceiptDetails.getReceiptMaster().getId());
		        	if(invoiceReceiptDetails.getReceiptMaster().getRtgs_utr_no().length()>0) {
		        		receiptModal.setRtgsUtrNo(invoiceReceiptDetails.getReceiptMaster().getRtgs_utr_no());
		        	}
	        	}else {
	        		invoiceNumber=invoiceNumber+" , "+invoiceReceiptDetails.getBillDetails().getInvoiceNo()+
	        				generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getBillDetails().getInvoice_seq());
	        	}
	        }
	        receiptModal.setInvoiceNumber(invoiceNumber);
	        ByteArrayInputStream bis = PrintReceipt.generateReceipt(receiptModal,headerName,subHeaderName);
	
	        HttpHeaders headers = new HttpHeaders();
	        String fileName=receiptModal.getReceiptNumber()+".pdf";
	        headers.add("Content-Disposition", "inline; filename="+fileName);
	
	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public ApplnUser getApplnUserDetails() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	ApplnUser applnUser=applnUserService.findByUserName(auth.getName());
    	if(applnUser != null && applnUser.getUserType() != null) {
    		userType=applnUser.getUserType();
    	}
    	return applnUser;
    }
    
	@RequestMapping(value="/delete-brand/{id}", method = RequestMethod.GET)
	public String deleteBrand(@PathVariable("id") Long id,
			HttpServletRequest request) {
		try {
			loadKeys();
			brandService.deleteBrand(id);
			request.setAttribute("success", "Brand Details deleted successfully");
		}catch(Exception e) {
			request.setAttribute("errorMsg", "Brand cannot be deleted because it is alread distributed to the customer.");
			e.printStackTrace();
		}
		Collection<BrandMaster> brandColls=new ArrayList<BrandMaster>();
		brandColls=brandService.showAllBrand();
		Collections.sort((List)brandColls ,new ComparatorAsc());
		request.setAttribute("brandMasters", brandColls);
		
		ApplnUser applnUser1=getApplnUserDetails();
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "brandMaster";
	}
	
	@RequestMapping(value= "/edit-brand/{id}", method = RequestMethod.GET)
	public String editBrand(@PathVariable("id") Long id, ModelMap model ) {
	    model.put("brandMaster", brandService.editBrand(id));
	    loadKeys();
	    ApplnUser applnUser1=getApplnUserDetails();
	    model.addAttribute("userType",userType);
		model.addAttribute("appName",appName);
	    return  "updateBrand";
	}
	
	@PostMapping("/updateBrand")
	public String updateBrand(@ModelAttribute("brandMaster") BrandMaster brandMaster,
			BindingResult bindingResult,HttpServletRequest request) {
		BrandMaster brandMaster2TempObj=brandService.findById(brandMaster.getId());
		loadKeys();
		if (bindingResult.hasErrors()) {
	        return "error";
	    }
		if(brandMaster2TempObj == null) {
			request.setAttribute("error", "Brand id is not found.");
			return "brandMaster";
		}
		brandService.saveBrand(brandMaster);
		request.setAttribute("success", "Brand Details updated successfully");
		Collection<BrandMaster> brandColls=new ArrayList<BrandMaster>();
		brandColls=brandService.showAllBrand();
		Collections.sort((List)brandColls ,new ComparatorAsc());
		request.setAttribute("brandMasters", brandColls);
		ApplnUser applnUser1=getApplnUserDetails();
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "brandMaster";
	}
	
	@RequestMapping(value="/delete-cutomerObj/{id}", method = RequestMethod.GET)
	public String deleteCustomer(@PathVariable("id") Long id,
			HttpServletRequest request) {
		try {
			loadKeys();
			customerService.deleteCustomer(id);;
			request.setAttribute("success", "Customer Details deleted successfully");
		}catch(Exception e) {
			request.setAttribute("errorMsg", "Customer cannot be deleted because items are already distributed to him/her.");
			e.printStackTrace();
		}
		Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser.getDepotMaster());
			customerColls=customerService.showAllCustomerByDepotWise(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
			customerColls=customerService.showAllCustomer();
		}
		request.setAttribute("depotList", depotColls);
		request.setAttribute("allCustomers", customerColls);
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "customerMaster";
	}
	
	@RequestMapping(value= "/edit-cutomerObj/{id}", method = RequestMethod.GET)
	public String editCustomer(@PathVariable("id") Long id, ModelMap model ) {
	    model.put("customerMaster", customerService.editCustomer(id));
	    Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		loadKeys();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
		}
		model.addAttribute("depotList", depotColls);
		model.addAttribute("userType",userType);
		model.addAttribute("appName",appName);
	    return  "updateCustomer";
	}
	
	@PostMapping("/updateCustomer")
	public String updateBrand(@ModelAttribute("customerMaster") CustomerMaster customerMaster,
			BindingResult bindingResult,HttpServletRequest request) {
		CustomerMaster customerMaster2TempObj=customerService.findById(customerMaster.getId());
		loadKeys();
		if (bindingResult.hasErrors()) {
	        return "error";
	    }
		if(customerMaster2TempObj == null) {
			request.setAttribute("error", "Customer id is not found.");
			return "customerMaster";
		}
		customerService.saveCustomer(customerMaster);
		request.setAttribute("success", "Customer Details updated successfully");
		
		Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser.getDepotMaster());
			customerColls=customerService.showAllCustomerByDepotWise(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
			customerColls=customerService.showAllCustomer();
		}
		request.setAttribute("depotList", depotColls);
		request.setAttribute("allCustomers", customerColls);
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "customerMaster";
	}
	
	@RequestMapping(value="/delete-depotObj/{id}", method = RequestMethod.GET)
	public String deleteDepot(@PathVariable("id") Long id,
			HttpServletRequest request) {
		try {
			loadKeys();
			depotService.deleteDepot(id);
			request.setAttribute("success", "Depot Details deleted successfully");
		}catch(Exception e) {
			request.setAttribute("errorMsg", "Depot cannot be deleted because items are already distributed to cusotmers.");
			e.printStackTrace();
		}
		Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
		}
		request.setAttribute("allDepots", depotColls);
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "depoMaster";
	}
	
	@RequestMapping(value= "/edit-depotObj/{id}", method = RequestMethod.GET)
	public String editDepot(@PathVariable("id") Long id, ModelMap model ) {
	    model.put("depotMaster", depotService.editDepot(id));
	    Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		loadKeys();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
		}
		model.addAttribute("depotList", depotColls);
		model.addAttribute("userType",userType);
		model.addAttribute("appName",appName);
	    return  "updateDepot";
	}
	
	@PostMapping("/updateDepot")
	public String updateDepot(@ModelAttribute("depotMaster") DepotMaster depotMaster,
			BindingResult bindingResult,HttpServletRequest request) {
		DepotMaster depotMaster2=depotService.findById(depotMaster.getId());
		loadKeys();
		if (bindingResult.hasErrors()) {
	        return "error";
	    }
		if(depotMaster2 == null) {
			request.setAttribute("error", "Depot id is not found.");
			return "depoMaster";
		}
		depotService.saveDepot(depotMaster);
		request.setAttribute("success", "Depot Details updated successfully");
		
		Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		ApplnUser applnUser=getApplnUserDetails();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
		}
		request.setAttribute("allDepots", depotColls);
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "depoMaster";
	}
	
	
	@GetMapping("/addKey")
	public String addKey(HttpServletRequest request) {
		Collection<KeyMaster> keyColls=new ArrayList<KeyMaster>();
		keyColls=keyMasterService.showAllKeyMasters();
		loadKeys();
		if(keyColls.size()>0)
		request.setAttribute("allKeys", keyColls);
		request.setAttribute("appName",appName);
		return "addkeyMaster";
	}
	
	@PostMapping("/saveKey")
	public String saveKey(@ModelAttribute KeyMaster keyMaster,
			BindingResult bindingResult,HttpServletRequest request) {
		try {
			Collection<KeyMaster> keyColls=new ArrayList<KeyMaster>();
			loadKeys();
			keyMasterService.saveKeyMaster(keyMaster);
			keyColls=keyMasterService.showAllKeyMasters();
			if(keyColls.size()>0)
			request.setAttribute("allKeys", keyColls);
			request.setAttribute("success", "Key details saved successfully");
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "Key details are not saved.");
		}
		request.setAttribute("appName",appName);
		return "addkeyMaster";
	}
	
	@GetMapping("/generateReceipt")
	public String generateReceipt(Model model,HttpServletRequest request) {
	    Collection<BillDetails> billDetailsColls=new ArrayList<BillDetails>();
		Collection<InvoiceReceiptDetails> receiptColls=new ArrayList<InvoiceReceiptDetails>();
		Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
		
		ApplnUser applnUser2=getApplnUserDetails();
		loadKeys();
		if(userType != null && userType.equals("USER")) {
			billDetailsColls=billDetailsService.showAllBillDetailsDepotWise(applnUser2.getDepotMaster());
			receiptColls=invoiceReceiptService.showAllReceiptBillDetailsWise(billDetailsColls);
			customerColls=customerService.showAllCustomerByDepotWise(applnUser2.getDepotMaster());
		}else if(userType != null && userType.equals("ADMIN")) {
			billDetailsColls=billDetailsService.showAllBillDetails();
			receiptColls=invoiceReceiptService.showAllInvoiceReceiptDetails();
			customerColls=customerService.showAllCustomer();
		}
		long receiptId=0;
		String invoiceNumber="";
		int counter=0;
		Collection<ReceiptModal> receiptModalColls=new ArrayList<ReceiptModal>();
		ReceiptModal receiptModal= new ReceiptModal();
		Collections.sort((List)receiptColls ,new ComparatorInvoiceRecect());
        for(Iterator itx=receiptColls.iterator();itx.hasNext();) {
        	InvoiceReceiptDetails invoiceReceiptDetails=(InvoiceReceiptDetails)itx.next();
        	counter++;
        	if(invoiceReceiptDetails != null && invoiceReceiptDetails.getReceiptMaster().getId()!=receiptId) {
        		receiptId=invoiceReceiptDetails.getReceiptMaster().getId();
        		if(counter != 1) {
        			receiptModal.setInvoiceNumber(invoiceNumber);
        			receiptModalColls.add(receiptModal);
        			invoiceNumber="";
        		}
        		receiptModal= new ReceiptModal();
	        	invoiceNumber=invoiceReceiptDetails.getBillDetails().getInvoiceNo()+
	        			generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getBillDetails().getInvoice_seq());
	        	receiptModal.setAmount(invoiceReceiptDetails.getReceiptMaster().getAmount());
	        	receiptModal.setCustomerName(invoiceReceiptDetails.getBillDetails().getCustomerMaster().getName_of_shop());
	        	receiptModal.setDepotName(invoiceReceiptDetails.getBillDetails().getDepotMaster().getName());
	        	receiptModal.setPaymentMode(invoiceReceiptDetails.getReceiptMaster().getPaymode());
	        	receiptModal.setReceiptDate(invoiceReceiptDetails.getReceiptMaster().getReceiptDate());
	        	receiptModal.setStatus(invoiceReceiptDetails.getReceiptMaster().getStatus());
	        	receiptModal.setReceiptNumber(invoiceReceiptDetails.getReceiptMaster().getReceiptNo()
	        			+generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getReceiptMaster().getReceipt_seq()));
	        	receiptModal.setReceiptMasterId(invoiceReceiptDetails.getReceiptMaster().getId());
	        	if(invoiceReceiptDetails.getReceiptMaster().getRtgs_utr_no().length()>0) {
	        		receiptModal.setRtgsUtrNo(invoiceReceiptDetails.getReceiptMaster().getRtgs_utr_no());
	        	}
        	}else {
        		invoiceNumber=invoiceNumber+" , "+invoiceReceiptDetails.getBillDetails().getInvoiceNo()+
        				generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getBillDetails().getInvoice_seq());
        		if(counter==receiptColls.size()) {
        			receiptModal.setInvoiceNumber(invoiceNumber);
        			receiptModalColls.add(receiptModal);
        		}
        	}
        }
		model.addAttribute("billDetailsList", billDetailsColls);
		model.addAttribute("receiptList", receiptModalColls);
		model.addAttribute("customerList", customerColls);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("loggedInApplnUserId", auth.getPrincipal());
		model.addAttribute("userType",userType);
		request.setAttribute("appName",appName);
	    return "generateReceipt";
    }
	
	@PostMapping("/payAndGenerateReceipt")
	public String payAndGenerateReceipt(@ModelAttribute ReceiptMaster receiptMaster,@RequestParam(value="billDetails", required = false) String  billDetails,
			@RequestParam("customerObject") String  customerObj,BindingResult bindingResult,HttpServletRequest request) {
		try {
			//Generate automatic receipt number
			ApplnUser applnUser2=getApplnUserDetails();
			SimpleDateFormat f = new SimpleDateFormat("MMM");
			Calendar now = Calendar.getInstance();
			
			String receiptNumber="SVP/"+now.get(Calendar.YEAR);
			now.add(Calendar.YEAR, 1);
			receiptNumber=receiptNumber+"-"+now.get(Calendar.YEAR)+"/"+
					applnUser2.getDepotMaster().getCode().substring(0, 3)+"/"+(f.format(receiptMaster.getReceiptDate())).toUpperCase()+"/R/";
			String checkDepo="/"+applnUser2.getDepotMaster().getCode().substring(0, 3)+"/";
			String checkMonth="/"+(f.format(receiptMaster.getReceiptDate())).toUpperCase()+"/R/";
			if(receiptNumber != null){
				receiptMaster.setReceiptNo(receiptNumber);
			}
			//long len=receiptService.getMaxReceiptSeq()+1;
			long counterReceipt=0;
			Collection getMaxReceSeq=invoiceReceiptService.showAllReceiptBillDetailsWise(billDetailsService.showAllBillDetailsDepotWise(applnUser2.getDepotMaster()));
			for(Iterator itxL=getMaxReceSeq.iterator();itxL.hasNext();) {
				InvoiceReceiptDetails invoiceReceObj=(InvoiceReceiptDetails)itxL.next();
				if(invoiceReceObj.getReceiptMaster().getReceiptNo() != null && invoiceReceObj.getReceiptMaster().getReceiptNo().contains(checkDepo) &&
						invoiceReceObj.getReceiptMaster().getReceiptNo().contains(checkMonth)) {
					counterReceipt=counterReceipt+1;
				}
			}
			long len=counterReceipt+1;
			receiptMaster.setReceipt_seq(len);
			if(billDetails != null && billDetails.length()>0) {
				receiptMaster.setStatus("Active");
			}else if(customerObj != null && customerObj.length()>0) {
				receiptMaster.setStatus("Advance");
			}
			receiptService.saveReceipt(receiptMaster);
			if(customerObj != null && customerObj.length()>0) {
				billDetails=saveAdvancePayment(customerObj);
			}
			String billDetailsIds[]=billDetails.split(",");
			for(int i=0;i<billDetailsIds.length;i++) {
				InvoiceReceiptDetails invoiceReceiptDetails=new InvoiceReceiptDetails();
				invoiceReceiptDetails.setReceiptMaster(receiptMaster);
				BillDetails billDetails2=billDetailsService.getBillDetails(Long.valueOf(billDetailsIds[i]));
				invoiceReceiptDetails.setBillDetails(billDetails2);
				invoiceReceiptService.saveInvoiceReceiptDetails(invoiceReceiptDetails);
			}
			Collection<BillDetails> billDetailsColls=new ArrayList<BillDetails>();
			Collection<InvoiceReceiptDetails> receiptColls=new ArrayList<InvoiceReceiptDetails>();
			Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
			loadKeys();
			if(userType != null && userType.equals("USER")) {
				billDetailsColls=billDetailsService.showAllBillDetailsDepotWise(applnUser2.getDepotMaster());
				receiptColls=invoiceReceiptService.showAllReceiptBillDetailsWise(billDetailsColls);
				customerColls=customerService.showAllCustomerByDepotWise(applnUser2.getDepotMaster());
			}else if(userType != null && userType.equals("ADMIN")) {
				billDetailsColls=billDetailsService.showAllBillDetails();
				receiptColls=invoiceReceiptService.showAllInvoiceReceiptDetails();
				customerColls=customerService.showAllCustomer();
			}
			
			long receiptId=0;
			String invoiceNumber="";
			int counter=0;
			Collection<ReceiptModal> receiptModalColls=new ArrayList<ReceiptModal>();
			ReceiptModal receiptModal= new ReceiptModal();
			Collections.sort((List)receiptColls ,new ComparatorInvoiceRecect());
	        for(Iterator itx=receiptColls.iterator();itx.hasNext();) {
	        	InvoiceReceiptDetails invoiceReceiptDetails=(InvoiceReceiptDetails)itx.next();
	        	counter++;
	        	if(invoiceReceiptDetails != null && invoiceReceiptDetails.getReceiptMaster().getId()!=receiptId) {
	        		receiptId=invoiceReceiptDetails.getReceiptMaster().getId();
	        		if(counter != 1) {
	        			receiptModal.setInvoiceNumber(invoiceNumber);
	        			receiptModalColls.add(receiptModal);
	        			invoiceNumber="";
	        		}
	        		receiptModal= new ReceiptModal();
		        	invoiceNumber=invoiceReceiptDetails.getBillDetails().getInvoiceNo()+
		        			generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getBillDetails().getInvoice_seq());
		        	receiptModal.setAmount(invoiceReceiptDetails.getReceiptMaster().getAmount());
		        	receiptModal.setCustomerName(invoiceReceiptDetails.getBillDetails().getCustomerMaster().getName_of_shop());
		        	receiptModal.setDepotName(invoiceReceiptDetails.getBillDetails().getDepotMaster().getName());
		        	receiptModal.setPaymentMode(invoiceReceiptDetails.getReceiptMaster().getPaymode());
		        	receiptModal.setReceiptDate(invoiceReceiptDetails.getReceiptMaster().getReceiptDate());
		        	receiptModal.setStatus(invoiceReceiptDetails.getReceiptMaster().getStatus());
		        	receiptModal.setReceiptNumber(invoiceReceiptDetails.getReceiptMaster().getReceiptNo()
		        			+generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getReceiptMaster().getReceipt_seq()));
		        	receiptModal.setReceiptMasterId(invoiceReceiptDetails.getReceiptMaster().getId());
		        	if(invoiceReceiptDetails.getReceiptMaster().getRtgs_utr_no().length()>0) {
		        		receiptModal.setRtgsUtrNo(invoiceReceiptDetails.getReceiptMaster().getRtgs_utr_no());
		        	}
	        	}else {
	        		invoiceNumber=invoiceNumber+" , "+invoiceReceiptDetails.getBillDetails().getInvoiceNo()+
	        				generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getBillDetails().getInvoice_seq());
	        		if(counter==receiptColls.size()) {
	        			receiptModal.setInvoiceNumber(invoiceNumber);
	        			receiptModalColls.add(receiptModal);
	        		}
	        	}
	        }
			request.setAttribute("billDetailsList", billDetailsColls);
			request.setAttribute("receiptList", receiptModalColls);
			request.setAttribute("customerList", customerColls);
			request.setAttribute("success", "Amount Paid successfully");
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Amount not paid.");
		}
		ApplnUser applnUser1=getApplnUserDetails();
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "generateReceipt";
	}
	
	private String saveAdvancePayment(String customerObj) {
		//If advance invoice of customer is present then fetch that invoice otherwise create new advance invoice.
		CustomerMaster customerMaster=customerService.findById(Long.valueOf(customerObj));
		BillDetails billDetails=billDetailsService.getBillDetailsByCustomerMasterAndStatus(customerMaster,"Advance");
		String billDetailsId=null;
		if(billDetails != null && billDetails.getId()>0) {
			billDetailsId=String.valueOf(billDetails.getId());
		}else {
			try {
				billDetails=new BillDetails();
				CustomerMaster customerMasterObj=customerService.findById(Long.valueOf(customerObj));
				billDetails.setCustomerMaster(customerMasterObj);
				billDetails.setDepotMaster(customerMasterObj.getDepotMaster());
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				ApplnUser applnUser=applnUserService.findByUserName(auth.getName());
				billDetails.setApplnUser(applnUser);
				ApplnUser applnUser2=getApplnUserDetails();
				SimpleDateFormat f = new SimpleDateFormat("MMM");
				Calendar now = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date dates=new Date();
				Date entryDate=new Date();
				
				String receiptNumber="SVP/"+now.get(Calendar.YEAR);
				now.add(Calendar.YEAR, 1);
				receiptNumber=receiptNumber+"-"+now.get(Calendar.YEAR)+"/"+
						applnUser2.getDepotMaster().getCode().substring(0, 3)+"/"+(f.format(entryDate)).toUpperCase()+"/I/";
				String checkDepo="/"+applnUser2.getDepotMaster().getCode().substring(0, 3)+"/";
				String checkMonth="/"+(f.format(entryDate)).toUpperCase()+"/I/";
				if(receiptNumber != null){
					billDetails.setInvoiceNo(receiptNumber);
				}
				//long len=billDetailsService.getMaxInvoiceSeq()+1;
				Collection lengthOfMaxInvoiceSeq=billDetailsService.showAllBillDetailsDepotWise(billDetails.getDepotMaster());
				long counter=0;
				for(Iterator<BillDetails> itxL=lengthOfMaxInvoiceSeq.iterator();itxL.hasNext();) {
					BillDetails billDe=(BillDetails)itxL.next();
					if(billDe.getInvoiceNo() != null && billDe.getInvoiceNo().contains(checkDepo) &&
							billDe.getInvoiceNo().contains(checkMonth)) {
						counter=counter+1;
					}
				}
				long len=counter+1;
				billDetails.setInvoice_seq(len);
				billDetails.setDate_s(dates);
				billDetails.setEntry_date(entryDate);
				billDetails.setStatus("Advance");
				
				billDetailsService.saveBillDetails(billDetails);
				billDetailsId=String.valueOf(billDetails.getId());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return billDetailsId;
	}

	@GetMapping("/searchBill")
	public String searchBill(Model model,HttpServletRequest request) {
	    Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
		Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
		Collection<InvoiceReceiptDetails> receiptColls=new ArrayList<InvoiceReceiptDetails>();
		Collection<BillDetails> billDetailsColls=new ArrayList<BillDetails>();
		Collection<InvoiceReceiptDetails> receiptCollect=new ArrayList<InvoiceReceiptDetails>();
		ApplnUser applnUser2=getApplnUserDetails();
		loadKeys();
		if(userType != null && userType.equals("USER")) {
			depotColls.add(applnUser2.getDepotMaster());
			customerColls=customerService.showAllCustomerByDepotWise(applnUser2.getDepotMaster());
			billDetailsColls=billDetailsService.showAllBillDetailsDepotWise(applnUser2.getDepotMaster());
			receiptColls=invoiceReceiptService.showAllReceiptBillDetailsWise(billDetailsColls);
			receiptCollect=invoiceReceiptService.showAllReceiptBillDetailsWise(billDetailsColls);
		}else if(userType != null && userType.equals("ADMIN")) {
			depotColls=depotService.showAllDepot();
			customerColls=customerService.showAllCustomer();
			billDetailsColls=billDetailsService.showAllBillDetails();
			receiptColls=invoiceReceiptService.showAllInvoiceReceiptDetails();
			receiptCollect=invoiceReceiptService.showAllReceiptBillDetailsWise(billDetailsColls);
		}
		model.addAttribute("depotList", depotColls);
		model.addAttribute("customerList", customerColls);
		model.addAttribute("receiptMainList", receiptCollect);
		model.addAttribute("receiptList", receiptColls);
		model.addAttribute("billDetailsList", billDetailsColls);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("loggedInApplnUserId", auth.getPrincipal());
		model.addAttribute("userType",userType);
		request.setAttribute("appName",appName);
	    return "searchBill";
    }
	
	
	@GetMapping("/searchReceipt")
	public String searchReceipt(Model model,HttpServletRequest request) {
			try {
		    Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
			Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
			Collection<InvoiceReceiptDetails> receiptColls=new ArrayList<InvoiceReceiptDetails>();
			Collection<InvoiceReceiptDetails> receiptCollect=new ArrayList<InvoiceReceiptDetails>();
			Collection<BillDetails> billDetailsColls=new ArrayList<BillDetails>();
			ApplnUser applnUser2=getApplnUserDetails();
			loadKeys();
			String invoiceNo=request.getParameter("billDetails");
			String receiptNo=request.getParameter("receiptMaster");
			String depo=request.getParameter("depotMaster");
			String cust=request.getParameter("customerMaster");
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date receiptDate=null;
			if(request.getParameter("receiptDate") != null && request.getParameter("receiptDate").length()>0) {
			 receiptDate=formatter.parse((String)request.getParameter("receiptDate"));
			}
			model.addAttribute("billDetails", invoiceNo);
			model.addAttribute("receiptMaster", receiptNo);
			model.addAttribute("depotMaster", depo);
			model.addAttribute("customerMaster", cust);
			model.addAttribute("receiptDate", receiptDate);
			Collection<BillDetails> tempBillDetails=new ArrayList<BillDetails>();
			if(userType != null && userType.equals("USER")) {
				depotColls.add(applnUser2.getDepotMaster());
				customerColls=customerService.showAllCustomerByDepotWise(applnUser2.getDepotMaster());
				billDetailsColls=billDetailsService.showAllBillDetailsDepotWise(applnUser2.getDepotMaster());
				receiptCollect=invoiceReceiptService.showAllReceiptBillDetailsWise(billDetailsColls);
			}else if(userType != null && userType.equals("ADMIN")) {
				depotColls=depotService.showAllDepot();
				customerColls=customerService.showAllCustomer();
				billDetailsColls=billDetailsService.showAllBillDetails();
				receiptCollect=invoiceReceiptService.showAllInvoiceReceiptDetails();
			}
			if(invoiceNo != null && invoiceNo.length()>0 || receiptNo != null && receiptNo.length()>0 ||
					depo != null && depo.length()>0 ||  cust != null && cust.length()>0 || receiptDate != null) {
				receiptColls=getReceiptDetails(billDetailsColls, depo, invoiceNo, receiptNo, cust, receiptDate);
			}else {
				receiptColls=invoiceReceiptService.showAllReceiptBillDetailsWise(billDetailsColls);
			}
			model.addAttribute("depotList", depotColls);
			model.addAttribute("customerList", customerColls);
			model.addAttribute("receiptMainList", receiptCollect);
			model.addAttribute("receiptList", receiptColls);
			model.addAttribute("billDetailsList", billDetailsColls);
		}catch (Exception e) {
			e.printStackTrace();
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("loggedInApplnUserId", auth.getPrincipal());
		model.addAttribute("userType",userType);
		request.setAttribute("appName",appName);
	    return "searchBill";
    }
	
	public Collection<InvoiceReceiptDetails> getReceiptDetails(Collection BillDetailsColl,String depoName,String invoiceNo,String receiptNo,String custName,
			Date receiptDate){
		Collection<InvoiceReceiptDetails> receiptColls=new ArrayList<InvoiceReceiptDetails>();
		Collection<InvoiceReceiptDetails> receiptCollsTemp=new ArrayList<InvoiceReceiptDetails>();
		Collection<BillDetails> tempBillDetails=new ArrayList<BillDetails>();
		for(Iterator itx=BillDetailsColl.iterator();itx.hasNext();) {
			BillDetails billDetails=(BillDetails)itx.next();
			String invoiceNoT=billDetails.getInvoiceNo()+generateInvoiceOrReceiptSeq(billDetails.getInvoice_seq());
			if(invoiceNo != null && invoiceNo.length()>0 && depoName != null && depoName.length()>0 
					&& custName != null && custName.length()>0) {
				if(invoiceNoT.equalsIgnoreCase(invoiceNo) &&
						billDetails.getDepotMaster().getName().equalsIgnoreCase(depoName) &&
						billDetails.getCustomerMaster().getName_of_shop().equalsIgnoreCase(custName)) {
					tempBillDetails.add(billDetails);
				}
			}else if(depoName != null && depoName.length()>0 && custName != null && custName.length()>0) {
				if(billDetails.getDepotMaster().getName().equalsIgnoreCase(depoName) &&
						billDetails.getCustomerMaster().getName_of_shop().equalsIgnoreCase(custName)) {
					tempBillDetails.add(billDetails);
				}
			}else if(invoiceNo != null && invoiceNo.length()>0 && custName != null && custName.length()>0) {
				if(billDetails.getCustomerMaster().getName_of_shop().equalsIgnoreCase(custName)
						&& invoiceNoT.equalsIgnoreCase(invoiceNo)) {
					tempBillDetails.add(billDetails);
				}
			}else if(invoiceNo != null && invoiceNo.length()>0 && depoName != null && depoName.length()>0 ) {
				if(billDetails.getDepotMaster().getName().equalsIgnoreCase(depoName) &&
						invoiceNoT.equalsIgnoreCase(invoiceNo)) {
					tempBillDetails.add(billDetails);
				}
			}else if(!(depoName != null && depoName.length()>0 && custName != null && custName.length()>0) &&
					invoiceNo != null && invoiceNo.length()>0 && billDetails.getInvoiceNo().equalsIgnoreCase(invoiceNo)) {
				tempBillDetails.add(billDetails);
			}else if(!(invoiceNo != null && invoiceNo.length()>0 && custName != null && custName.length()>0 ) &&
					depoName != null && depoName.length()>0 && billDetails.getDepotMaster().getName().equalsIgnoreCase(depoName)) {
				tempBillDetails.add(billDetails);
			}else if(!(invoiceNo != null && invoiceNo.length()>0 && depoName != null && depoName.length()>0) &&
					custName != null && custName.length()>0 && billDetails.getCustomerMaster().getName_of_shop().equalsIgnoreCase(custName)) {
				tempBillDetails.add(billDetails);
			}
		}
		if(tempBillDetails.size()>0) {
			receiptCollsTemp=invoiceReceiptService.showAllReceiptBillDetailsWise(tempBillDetails);
		}else if(!(invoiceNo != null && invoiceNo.length()>0 || depoName != null && depoName.length()>0 
				|| custName != null && custName.length()>0)) {
			receiptCollsTemp=invoiceReceiptService.showAllReceiptBillDetailsWise(BillDetailsColl);
		}
		for(Iterator itex=receiptCollsTemp.iterator();itex.hasNext();) {
			InvoiceReceiptDetails receiptMasterObj=(InvoiceReceiptDetails)itex.next();
			String receiptNoT=receiptMasterObj.getReceiptMaster().getReceiptNo()+
					generateInvoiceOrReceiptSeq(receiptMasterObj.getReceiptMaster().getReceipt_seq());
			if(receiptNo != null && receiptNo.length()>0 && receiptDate != null) {
				if(receiptNoT.equalsIgnoreCase(receiptNo) &&
						receiptMasterObj.getReceiptMaster().getReceiptDate().compareTo(receiptDate) == 0) {
					receiptColls.add(receiptMasterObj);
				}
			}else if(receiptNo != null && receiptNo.length()>0){
				if(receiptNoT.equalsIgnoreCase(receiptNo)) {
					receiptColls.add(receiptMasterObj);
				}
			}else if(receiptDate != null){
				if(receiptMasterObj.getReceiptMaster().getReceiptDate().compareTo(receiptDate) == 0) {
					receiptColls.add(receiptMasterObj);
				}
			}
		}
		if(receiptCollsTemp.size()>0 && receiptNo=="" && receiptDate==null) {
			receiptColls.addAll(receiptCollsTemp);
		}
		return receiptColls;
	}
	
	@RequestMapping(value = "/download-bulkReceipt", method = RequestMethod.POST,produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateBulkReceipt(@RequestParam("invoiceReceiptIds") List<Long> invoiceReceiptIds,
    		@RequestParam(value="action", required=true) String action) throws IOException {
    	try {
    		loadKeys();
    		if(action != null && action.equalsIgnoreCase("singleReceipt")) {
    			
    		}
    		Collection<InvoiceReceiptDetails> invoiceReceiptColls=new ArrayList<InvoiceReceiptDetails>();
    		for (int i = 0; i < invoiceReceiptIds.size(); i++) {
    			InvoiceReceiptDetails invoiceReceiptDetails=invoiceReceiptService.editInvoiceReceiptIds((Long) invoiceReceiptIds.get(i));
    			if(invoiceReceiptDetails != null) {
    				invoiceReceiptColls.add(invoiceReceiptDetails);
    			}
    		}
	        ByteArrayInputStream bis = PrintBulkReceipt.generateReceipt(invoiceReceiptColls,headerName,subHeaderName);
	        HttpHeaders headers = new HttpHeaders();
	        String fileName="bulkReceipt.pdf";
	        headers.add("Content-Disposition", "inline; filename="+fileName);
	
	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
	public String generateInvoiceOrReceiptSeq(long num) {
		String number="";
		if(num<10) {
			number="000"+num;
		}else if(num<100){
			number="00"+num;
		}else if(num<1000){
			number="0"+num;
		}
		return number;
	}
	
    @RequestMapping(value = "/cancel-billDetail/{id}", method = RequestMethod.GET)
    public String cancelBill(@PathVariable("id") Long id,HttpServletRequest request,Model model) throws IOException {
    	try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			loadKeys();
			model.addAttribute("loggedInApplnUserId", auth.getPrincipal());
			BillDetails billDetails=billDetailsService.getBillDetails(id);
			billDetails.setStatus("Cancelled");
			billDetailsService.saveBillDetails(billDetails);
			ApplnUser applnUser2=getApplnUserDetails();
			
			Collection<DepotMaster> depotColls=new ArrayList<DepotMaster>();
			Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
			Collection<BrandMaster> brandColls=new ArrayList<BrandMaster>();
			Collection<BillDetails> billDetailsColls=new ArrayList<BillDetails>();
			
			if(userType != null && userType.equals("USER")) {
				depotColls.add(applnUser2.getDepotMaster());
				customerColls=customerService.showAllCustomerByDepotWise(applnUser2.getDepotMaster());
				billDetailsColls=billDetailsService.showAllBillDetailsDepotWise(applnUser2.getDepotMaster());
			}else if(userType != null && userType.equals("ADMIN")) {
				depotColls=depotService.showAllDepot();
				customerColls=customerService.showAllCustomer();
				billDetailsColls=billDetailsService.showAllBillDetails();
			}
			brandColls=brandService.showAllBrand();
			Collections.sort((List)brandColls ,new ComparatorAsc());
			model.addAttribute("depotList", depotColls);
			model.addAttribute("customerList", customerColls);
			model.addAttribute("brandList", brandColls);
			model.addAttribute("billDetailsList", billDetailsColls);
			request.setAttribute("success", "Invoice is cancelled successfully");
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Invoice is not cancelled.");
		}
		request.setAttribute("userType",userType);
		request.setAttribute("appName",appName);
		return "homepage";
    }
    
    @RequestMapping(value = "/cancel-billDetail-receipt/{id}", method = RequestMethod.GET)
    public String cancelReceipt(@PathVariable("id") Long id,HttpServletRequest request,Model model) throws IOException {
	    Collection<BillDetails> billDetailsColls=new ArrayList<BillDetails>();
		Collection<InvoiceReceiptDetails> receiptColls=new ArrayList<InvoiceReceiptDetails>();
		Collection<CustomerMaster> customerColls=new ArrayList<CustomerMaster>();
		try {
			ReceiptMaster receiptObj=receiptService.editReceitp(id);
			receiptObj.setStatus("Cancelled");
			receiptService.saveReceipt(receiptObj);
			ApplnUser applnUser2=getApplnUserDetails();
			loadKeys();
			if(userType != null && userType.equals("USER")) {
				billDetailsColls=billDetailsService.showAllBillDetailsDepotWise(applnUser2.getDepotMaster());
				receiptColls=invoiceReceiptService.showAllReceiptBillDetailsWise(billDetailsColls);
				customerColls=customerService.showAllCustomerByDepotWise(applnUser2.getDepotMaster());
			}else if(userType != null && userType.equals("ADMIN")) {
				billDetailsColls=billDetailsService.showAllBillDetails();
				receiptColls=invoiceReceiptService.showAllInvoiceReceiptDetails();
				customerColls=customerService.showAllCustomer();
			}
			long receiptId=0;
			String invoiceNumber="";
			int counter=0;
			Collection<ReceiptModal> receiptModalColls=new ArrayList<ReceiptModal>();
			ReceiptModal receiptModal= new ReceiptModal();
			Collections.sort((List)receiptColls ,new ComparatorInvoiceRecect());
	        for(Iterator itx=receiptColls.iterator();itx.hasNext();) {
	        	InvoiceReceiptDetails invoiceReceiptDetails=(InvoiceReceiptDetails)itx.next();
	        	counter++;
	        	if(invoiceReceiptDetails != null && invoiceReceiptDetails.getReceiptMaster().getId()!=receiptId) {
	        		receiptId=invoiceReceiptDetails.getReceiptMaster().getId();
	        		if(counter != 1) {
	        			receiptModal.setInvoiceNumber(invoiceNumber);
	        			receiptModalColls.add(receiptModal);
	        			invoiceNumber="";
	        		}
	        		receiptModal= new ReceiptModal();
		        	invoiceNumber=invoiceReceiptDetails.getBillDetails().getInvoiceNo()+
		        			generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getBillDetails().getInvoice_seq());
		        	receiptModal.setAmount(invoiceReceiptDetails.getReceiptMaster().getAmount());
		        	receiptModal.setCustomerName(invoiceReceiptDetails.getBillDetails().getCustomerMaster().getName_of_shop());
		        	receiptModal.setDepotName(invoiceReceiptDetails.getBillDetails().getDepotMaster().getName());
		        	receiptModal.setPaymentMode(invoiceReceiptDetails.getReceiptMaster().getPaymode());
		        	receiptModal.setReceiptDate(invoiceReceiptDetails.getReceiptMaster().getReceiptDate());
		        	receiptModal.setStatus(invoiceReceiptDetails.getReceiptMaster().getStatus());
		        	receiptModal.setReceiptNumber(invoiceReceiptDetails.getReceiptMaster().getReceiptNo()
		        			+generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getReceiptMaster().getReceipt_seq()));
		        	receiptModal.setReceiptMasterId(invoiceReceiptDetails.getReceiptMaster().getId());
		        	if(invoiceReceiptDetails.getReceiptMaster().getRtgs_utr_no().length()>0) {
		        		receiptModal.setRtgsUtrNo(invoiceReceiptDetails.getReceiptMaster().getRtgs_utr_no());
		        	}
	        	}else {
	        		invoiceNumber=invoiceNumber+" , "+invoiceReceiptDetails.getBillDetails().getInvoiceNo()+
	        				generateInvoiceOrReceiptSeq(invoiceReceiptDetails.getBillDetails().getInvoice_seq());
	        		if(counter==receiptColls.size()) {
	        			receiptModal.setInvoiceNumber(invoiceNumber);
	        			receiptModalColls.add(receiptModal);
	        		}
	        	}
	        }
			model.addAttribute("billDetailsList", billDetailsColls);
			model.addAttribute("receiptList", receiptModalColls);
			model.addAttribute("customerList", customerColls);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			model.addAttribute("loggedInApplnUserId", auth.getPrincipal());
			model.addAttribute("userType",userType);
			request.setAttribute("appName",appName);
			request.setAttribute("success", "Receipt is cancelled successfully.");
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Receipt is not cancelled.");
		}
	    return "generateReceipt";
    
    }
    
	public static String getUserType() {
		return userType;
	}

	public static void setUserType(String userType) {
		ApplicationController.userType = userType;
	}

	public long[] getReceiptIds() {
		return receiptIds;
	}

	public void setReceiptIds(long[] receiptIds) {
		this.receiptIds = receiptIds;
	}
}

