//package com.simplefitness.mem.controller;
//
//import static com.common.util.Constants.GSON;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.simplefitness.mem.service.impl.MemServiceImpl;
//import com.simplefitness.mem.service.intf.MemServiceIntf;
//import com.simplefitness.mem.vo.Member;
//
//
//@WebServlet("/member/forgetpass")
//public class ForgetPassServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	@Autowired
//	private MemServiceIntf SERVICE = new MemServiceImpl();
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		BufferedReader br = request.getReader();
//        String json = br.readLine();
//        Member member = GSON.fromJson(json, Member.class);
//        
//        member = SERVICE.forgetPass(member);
//		
//		if (member.isSuccessful()) {
//			final HttpSession session = request.getSession();
//			session.setAttribute("forget", member);
//		}
//        
//        PrintWriter pw = response.getWriter();
//        pw.print(GSON.toJson(member));
//        
//	}
//
//}
