//package com.simplefitness.mem.controller;
//
//import static com.common.util.GsonUtil.writePojo2Json;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.simplefitness.mem.service.impl.MemServiceImpl;
//import com.simplefitness.mem.service.intf.MemServiceIntf;
//import com.simplefitness.mem.vo.Member;
//
//
//@WebServlet("/member/getAll")
//public class GetAllMemberServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	@Autowired
//	private MemServiceIntf SERVICE = new MemServiceImpl();
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		List<Member> member = SERVICE.findAll();
//		
//		writePojo2Json(response, member);
//	}
//
//}
