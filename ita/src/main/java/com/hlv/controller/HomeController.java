package com.hlv.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hlv.bean.Message;
import com.hlv.bean.UsersBean;
import com.hlv.entity.Users;
import com.hlv.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String Home(Model model) {
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String Upload(Model model) {
		return "upload";
	}

	@RequestMapping(value = "/upload/2003", method = RequestMethod.POST)
	public String processExcel(@ModelAttribute("bean") @Valid UsersBean bean, Model model,
			@RequestParam("excelfile") MultipartFile excelfile) {
		try {
			userService.addListUser(getUsers(excelfile, true));

			bean.addMessage(Message.SUCCESS, "Upload Excel 2003 Successfull");
			model.addAttribute("bean", bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/upload";
	}
	
	private static List<Users> getUsers(MultipartFile excelfile, boolean isXls) throws IOException {
		List<Users> lstUser = new ArrayList<Users>();
		int i = 0;
		// Creates a workbook object from the uploaded excelfile
		Workbook workbook = (isXls) ? new HSSFWorkbook(excelfile.getInputStream()) : new XSSFWorkbook(excelfile.getInputStream());
		// Creates a worksheet object representing the first sheet
		Sheet worksheet = workbook.getSheetAt(0);
		// Reads the data in excel file until last row is encountered
		while (i <= worksheet.getLastRowNum()) {
			// Creates an object for the UserInfo Model
			Users user = new Users();
			// Creates an object representing a single row in excel
			Row row = worksheet.getRow(i++);
			// Sets the Read data to the model class
			// user.setId((int) row.getCell(0).getNumericCellValue());
			user.setUsername(row.getCell(1).getStringCellValue());
			user.setInputDate(row.getCell(2).getDateCellValue());
			// persist data into database in here
			lstUser.add(user);
		}
		workbook.close();
		
		return lstUser;
	}

	@RequestMapping(value = "/upload/2007", method = RequestMethod.POST)
	public String processExcel2007(@ModelAttribute("bean") @Valid UsersBean bean, Model model,
			@RequestParam("excelfile2007") MultipartFile excelfile) {
		try {
			userService.addListUser(getUsers(excelfile, false));

			bean.addMessage(Message.SUCCESS, "Upload Excel 2007 Successfull");
			model.addAttribute("bean", bean);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/upload";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUsers(Model model) {
		model.addAttribute("listUsers", this.userService.listUsers());
		return "users";
	}

	@RequestMapping("/remove/{id}")
	public String removeUsers(@PathVariable("id") int id) {
		this.userService.removeUsers(id);
		return "redirect:/users";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addUser(@ModelAttribute Users users) {
		return "adduser";

	}

	@RequestMapping(value = "/save")
	public String saveUser(@ModelAttribute Users users) {
		this.userService.addUsers(users);
		return "redirect:/users";
	}

	@RequestMapping(value = "/edituserss", method = RequestMethod.GET)
	public ModelAndView editContact(HttpServletRequest request) {
		/*
		 * int id = Integer.parseInt(request.getParameter("id")); Users users =
		 * userService.getUsers(id);
		 */
		ModelAndView model = new ModelAndView("edituser");
		/* model.addObject("users", users); */

		return model;

	}

	@RequestMapping("/edit/{id}")
	public String updateUsers(@PathVariable("id") int id, Model model) {
		model.addAttribute("users", this.userService.getUsers(id));

		// this.userService.updateUsers(id);
		return "edit";
	}

	@RequestMapping(value = "/edit/editSave")
	public String updateUser(@ModelAttribute Users users) {

		this.userService.editsaveUsers(users);
		return "redirect:/users";
	}

	@RequestMapping(value = "/listing", method = RequestMethod.GET)
	public String pagination(@ModelAttribute Users users) {
		this.userService.getpagination(users);
		return "PageNation";
	}

	@RequestMapping(value = "------------/index")
	public String savepagination(@ModelAttribute Users users) {

		this.userService.savepagination(users);

		return "redirect:/upload";

	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String SearchUsername(@ModelAttribute Users users) {
		return "search";

	}

	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String Upload(@ModelAttribute Users users) {
		return "upload";

	}

	@RequestMapping(value = "/searchByName", method = RequestMethod.GET)
	public String SearchByName(@RequestParam String username) {
		this.userService.searchByName(username);
		return "users";
	}

}