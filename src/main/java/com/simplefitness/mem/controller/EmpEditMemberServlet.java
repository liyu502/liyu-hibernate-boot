//package com.simplefitness.mem.controller;
//
//import static com.common.util.GsonUtil.json2Pojo;
//import static com.common.util.GsonUtil.writePojo2Json;
//
//import java.io.IOException;
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
//@WebServlet("/member/empEditMember")
//public class EmpEditMemberServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	@Autowired
//	private MemServiceIntf SERVICE = new MemServiceImpl();
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		Member member = json2Pojo(request, Member.class);
//		
//		member = SERVICE.empEdit(member);
//        
//        writePojo2Json(response, member);
//	}
//
//}
