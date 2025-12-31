package com.core.aiProject5core;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.core.aiProject5core.entity.Address;
import com.core.aiProject5core.entity.Admin;
import com.core.aiProject5core.entity.Customer;
import com.core.aiProject5core.entity.Dealer;
import com.core.aiProject5core.entity.Member;
import com.core.aiProject5core.entity.Role;
import com.core.aiProject5core.service.AdminService;
import com.core.aiProject5core.service.CustomerService;
import com.core.aiProject5core.service.DealerService;
import com.core.aiProject5core.service.MemberService;

@SpringBootApplication
public class AiProject5coreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiProject5coreApplication.class, args);
	}
	
	
	//@Bean
	public CommandLineRunner run (MemberService memberService, CustomerService customerService, DealerService dealerService, AdminService adminService) throws Exception {
		return (String[] args) -> {
			
			/*
			 * 딜러
			 */
			Member dealer0 = new Member();
			dealer0.setMemberId("jihun004");
			dealer0.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			dealer0.setPassword2(dealer0.getPassword());
			dealer0.setName("최지훈");
			dealer0.setPhone("010-4567-8901");
			dealer0.setEmail("jihun4@naver.com");
			dealer0.setGender("남성");
			dealer0.setJoinDate(LocalDateTime.of(2023, 3, 28, 0, 0));
			dealer0.setRole(Role.DEALER);
			Address add0 = new Address();
			add0.setCountry("한국");
			add0.setZipcode("22009");
			add0.setBasicAddress("인천시 연수구 송도동 101");
			add0.setDetailAddress("101동 101호");
			dealer0.setAddress(add0);
			Dealer d0 = new Dealer();
			memberService.saveMember(dealer0);
			dealerService.saveDealer(d0, dealer0);
			
			Member dealer1 = new Member();
			dealer1.setMemberId("hana011");
			dealer1.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			dealer1.setPassword2(dealer1.getPassword());
			dealer1.setName("한서희");
			dealer1.setPhone("010-1234-5678");
			dealer1.setEmail("kimhana11@naver.com");
			dealer1.setGender("여성");
			dealer1.setJoinDate(LocalDateTime.of(2024, 5, 18, 0, 0));
			dealer1.setRole(Role.DEALER);
			Address add1 = new Address();
			add1.setCountry("한국");
			add1.setZipcode("01972");
			add1.setBasicAddress("서울시 종로구 청계천로 432");
			add1.setDetailAddress("2001동 2107호");
			dealer1.setAddress(add1);
			Dealer d1 = new Dealer();
			memberService.saveMember(dealer1);
			dealerService.saveDealer(d1, dealer1);
			
			// 관리자
			Member admin = new Member();
			admin.setMemberId("admin");
			admin.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			admin.setPassword2(dealer1.getPassword());
			admin.setName("유상윤");
			admin.setPhone("010-5353-2424");
			admin.setEmail("yusanyun@naver.com");
			admin.setGender("남성");
			admin.setJoinDate(LocalDateTime.of(2024, 7, 20, 0, 0));
			admin.setRole(Role.ADMIN);
			Address add2 = new Address();
			add2.setCountry("한국");
			add2.setZipcode("06313");
			add2.setBasicAddress("서울 강남구 논현로 16");
			add2.setDetailAddress("성동아파트 111호");	
			Admin ad1 = new Admin();
			memberService.saveMember(admin);
			adminService.saveAdmin(ad1, admin);
			
			/*
			 * 고객
			 */
			Member m0 = new Member();
			m0.setMemberId("14798");
			m0.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m0.setPassword2(m0.getPassword());
			m0.setName("강주온");
			m0.setPhone("010-6502-8753");
			m0.setEmail("janisi@naver.com");
			m0.setGender("남성");
			m0.setJoinDate(LocalDateTime.of(2023, 4, 1, 0, 0));
			m0.setRole(Role.CUSTOMER);
			Address a0 = new Address();
			a0.setCountry("한국");
			a0.setZipcode("06675");
			a0.setBasicAddress("서울시 서초구 서초대로 613");
			a0.setDetailAddress("서초아파트 101동 101호");
			m0.setAddress(a0);
			Customer c0 = new Customer();
			c0.setMember(m0);
			c0.setPurchaseCount(1);
			c0.setSegment("일반");
			memberService.saveMember(m0);
			customerService.saveCustomer(c0, m0);
			
			Member m1 = new Member();
			m1.setMemberId("kyoungsoo031");
			m1.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m1.setPassword2(m1.getPassword());
			m1.setName("김경수");
			m1.setPhone("010-1111-2222");
			m1.setEmail("kyoungsoo31@naver.com");
			m1.setGender("남성");
			m1.setJoinDate(LocalDateTime.of(2025, 2, 9, 0, 0));
			m1.setRole(Role.CUSTOMER);
			Address a1 = new Address();
			a1.setCountry("한국");
			a1.setZipcode("01064");
			a1.setBasicAddress("서울시 강북구 번1동 123");
			a1.setDetailAddress("강북아파트 1203동 203호");
			m1.setAddress(a1);
			Customer c1 = new Customer();
			c1.setMember(m1);
			c1.setPurchaseCount(5);
			c1.setSegment("vip");
			memberService.saveMember(m1);
			customerService.saveCustomer(c1, m1);
			
			Member m2 = new Member();
			m2.setMemberId("daeun026");
			m2.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m2.setPassword2(m2.getPassword());
			m2.setName("김다은");
			m2.setPhone("010-6789-0123");
			m2.setEmail("daeun26@naver.com");
			m2.setGender("여성");
			m2.setJoinDate(LocalDateTime.of(2025, 2, 28, 0, 0));
			m2.setRole(Role.CUSTOMER);
			Address a2 = new Address();
			a2.setCountry("한국");
			a2.setZipcode("47371");
			a2.setBasicAddress("부산시 부산진구 중앙대로 258");
			a2.setDetailAddress("진구아파트 102동 1205호");
			m2.setAddress(a2);
			Customer c2 = new Customer();
			c2.setMember(m2);
			c2.setPurchaseCount(1);
			c2.setSegment("일반");
			memberService.saveMember(m2);
			customerService.saveCustomer(c2, m2);
			
			Member m3 = new Member();
			m3.setMemberId("minsu001");
			m3.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m3.setPassword2(m3.getPassword());
			m3.setName("김민수");
			m3.setPhone("010-1234-5678");
			m3.setEmail("insu1@naver.com");
			m3.setGender("남성");
			m3.setJoinDate(LocalDateTime.of(2023, 4, 28, 0, 0));
			m3.setRole(Role.CUSTOMER);
			Address a3 = new Address();
			a3.setCountry("한국");
			a3.setZipcode("06133");
			a3.setBasicAddress("서울시 강남구 테헤란로 123");
			a3.setDetailAddress("테헤란빌라 101동 402호");
			m3.setAddress(a3);
			Customer c3 = new Customer();
			c3.setMember(m3);
			c3.setPurchaseCount(1);
			c3.setSegment("일반");
			memberService.saveMember(m3);
			customerService.saveCustomer(c3, m3);
			
			Member m4 = new Member();
			m4.setMemberId("seunghyun020");
			m4.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m4.setPassword2(m4.getPassword());
			m4.setName("김승현");
			m4.setPhone("010-0123-4567");
			m4.setEmail("seunghyun20@naver.com");
			m4.setGender("남성");
			m4.setJoinDate(LocalDateTime.of(2023, 9, 18, 0, 0));
			m4.setRole(Role.CUSTOMER);
			Address a4 = new Address();
			a4.setCountry("한국");
			a4.setZipcode("28669");
			a4.setBasicAddress("충청북도 청주시 서원구 모충로 98");
			a4.setDetailAddress("모충빌라 401동 202호");
			m4.setAddress(a4);
			Customer c4 = new Customer();
			c4.setMember(m4);
			c4.setPurchaseCount(1);
			c4.setSegment("일반");
			memberService.saveMember(m4);
			customerService.saveCustomer(c4, m4);
			
			Member m5 = new Member();
			m5.setMemberId("youngcheol006");
			m5.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m5.setPassword2(m5.getPassword());
			m5.setName("김영철");
			m5.setPhone("010-6789-0123");
			m5.setEmail("youngcheol6@naver.com");
			m5.setGender("남성");
			m5.setJoinDate(LocalDateTime.of(2023, 2, 1, 0, 0));
			m5.setRole(Role.CUSTOMER);
			Address a5 = new Address();
			a5.setCountry("한국");
			a5.setZipcode("28669");
			a5.setBasicAddress("서울시 서초구 서초대로 654");
			a5.setDetailAddress("방배아파트 303동 1502호");
			m5.setAddress(a5);
			Customer c5 = new Customer();
			c5.setMember(m5);
			c5.setPurchaseCount(1);
			c5.setSegment("일반");
			memberService.saveMember(m5);
			customerService.saveCustomer(c5, m5);
			
			Member m6 = new Member();
			m6.setMemberId("sujin002");
			m6.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m6.setPassword2(m6.getPassword());
			m6.setName("김윤희");
			m6.setPhone("010-2345-6789");
			m6.setEmail("sujin2@naver.com");
			m6.setGender("여성");
			m6.setJoinDate(LocalDateTime.of(2023, 7, 11, 0, 0));
			m6.setRole(Role.CUSTOMER);
			Address a6 = new Address();
			a6.setCountry("한국");
			a6.setZipcode("48059");
			a6.setBasicAddress("부산시 해운대구 센텀동로 456");
			a6.setDetailAddress("방배아파트 303동 1502호");
			m6.setAddress(a6);
			Customer c6 = new Customer();
			c6.setMember(m6);
			c6.setPurchaseCount(0);
			c6.setSegment("일반");
			memberService.saveMember(m6);
			customerService.saveCustomer(c6, m6);
			
			Member m7 = new Member();
			m7.setMemberId("jaewon040");
			m7.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m7.setPassword2(m7.getPassword());
			m7.setName("김재원");
			m7.setPhone("010-1234-5268");
			m7.setEmail("jaewon40@naver.com");
			m7.setGender("남성");
			m7.setJoinDate(LocalDateTime.of(2025, 11, 25, 0, 0));
			m7.setRole(Role.CUSTOMER);
			Address a7 = new Address();
			a7.setCountry("한국");
			a7.setZipcode("01931");
			a7.setBasicAddress("서울시 강동구 올림픽로 112");
			a7.setDetailAddress("선사현대아파트 112동 1101호");
			m7.setAddress(a7);
			Customer c7 = new Customer();
			c7.setMember(m7);
			c7.setPurchaseCount(1);
			c7.setSegment("일반");
			memberService.saveMember(m7);
			customerService.saveCustomer(c7, m7);
			
			Member m8 = new Member();
			m8.setMemberId("jinsu035");
			m8.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m8.setPassword2(m8.getPassword());
			m8.setName("김진수");
			m8.setPhone("010-6789-9876");
			m8.setEmail("jinsu35@naver.com");
			m8.setGender("남성");
			m8.setJoinDate(LocalDateTime.of(2023, 1, 2, 0, 0));
			m8.setRole(Role.CUSTOMER);
			Address a8 = new Address();
			a8.setCountry("한국");
			a8.setZipcode("20654");
			a8.setBasicAddress("경기도 수원시 장안구 정자동 875");
			a8.setDetailAddress("장안빌라 707동 503호");
			m8.setAddress(a8);
			Customer c8 = new Customer();
			c8.setMember(m8);
			c8.setPurchaseCount(2);
			c8.setSegment("일반");
			memberService.saveMember(m8);
			customerService.saveCustomer(c8, m8);
			
			Member m9 = new Member();
			m9.setMemberId("minjeong008");
			m9.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m9.setPassword2(m9.getPassword());
			m9.setName("박민정");
			m9.setPhone("010-8901-2345");
			m9.setEmail("minjeong8@naver.com");
			m9.setGender("여성");
			m9.setJoinDate(LocalDateTime.of(2024, 4, 2, 0, 0));
			m9.setRole(Role.CUSTOMER);
			Address a9 = new Address();
			a9.setCountry("한국");
			a9.setZipcode("40324");
			a9.setBasicAddress("울산시 남구 수암로 234");
			a9.setDetailAddress("남구아파트 301동 2202호");
			m9.setAddress(a9);
			Customer c9 = new Customer();
			c9.setMember(m9);
			c9.setPurchaseCount(1);
			c9.setSegment("일반");
			memberService.saveMember(m9);
			customerService.saveCustomer(c9, m9);
			
			Member m10 = new Member();
			m10.setMemberId("suyeon033");
			m10.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m10.setPassword2(m10.getPassword());
			m10.setName("박수연");
			m10.setPhone("010-2345-5432");
			m10.setEmail("suyeon33@naver.com");
			m10.setGender("여성");
			m10.setJoinDate(LocalDateTime.of(2022, 8, 15, 0, 0));
			m10.setRole(Role.CUSTOMER);
			Address a10 = new Address();
			a10.setCountry("한국");
			a10.setZipcode("06012");
			a10.setBasicAddress("서울특별시 강남구 청담동 106");
			a10.setDetailAddress("에테르노 청담 1701호");
			m10.setAddress(a10);
			Customer c10 = new Customer();
			c10.setMember(m10);
			c10.setPurchaseCount(5);
			c10.setSegment("vip");
			memberService.saveMember(m10);
			customerService.saveCustomer(c10, m10);
			
			Member m11 = new Member();
			m11.setMemberId("yeonghee003");
			m11.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m11.setPassword2(m11.getPassword());
			m11.setName("박영희");
			m11.setPhone("010-3456-7890");
			m11.setEmail("yeonghee3@naver.com");
			m11.setGender("여성");
			m11.setJoinDate(LocalDateTime.of(2024, 1, 25, 0, 0));
			m11.setRole(Role.CUSTOMER);
			Address a11 = new Address();
			a11.setCountry("한국");
			a11.setZipcode("51359");
			a11.setBasicAddress("대구시 수성구 수성로 789");
			a11.setDetailAddress("수성아파트 1001동 1205호");
			m11.setAddress(a11);
			Customer c11 = new Customer();
			c11.setMember(m11);
			c11.setPurchaseCount(2);
			c11.setSegment("일반");
			memberService.saveMember(m11);
			customerService.saveCustomer(c11, m11);
			
			Member m12 = new Member();
			m12.setMemberId("joonseok028");
			m12.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m12.setPassword2(m12.getPassword());
			m12.setName("박준석");
			m12.setPhone("010-8901-2345");
			m12.setEmail("joonseok28@naver.com");
			m12.setGender("남성");
			m12.setJoinDate(LocalDateTime.of(2024, 12, 4, 0, 0));
			m12.setRole(Role.CUSTOMER);
			Address a12 = new Address();
			a12.setCountry("한국");
			a12.setZipcode("07513");
			a12.setBasicAddress("서울시 송파구 잠실로 123");
			a12.setDetailAddress("현대아파트 101동 2305호");
			m12.setAddress(a12);
			Customer c12 = new Customer();
			c12.setMember(m12);
			c12.setPurchaseCount(0);
			c12.setSegment("일반");
			memberService.saveMember(m12);
			customerService.saveCustomer(c12, m12);
			
			Member m13 = new Member();
			m13.setMemberId("jisu022");
			m13.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m13.setPassword2(m13.getPassword());
			m13.setName("박지수");
			m13.setPhone("010-2345-6789");
			m13.setEmail("parkjisu22@naver.com");
			m13.setGender("여성");
			m13.setJoinDate(LocalDateTime.of(2025, 10, 24, 0, 0));
			m13.setRole(Role.CUSTOMER);
			Address a13 = new Address();
			a13.setCountry("한국");
			a13.setZipcode("08442");
			a13.setBasicAddress("서울시 동대문구 왕산로 233");
			a13.setDetailAddress("자이아파트 103동 2405호");
			m13.setAddress(a13);
			Customer c13 = new Customer();
			c13.setMember(m13);
			c13.setPurchaseCount(0);
			c13.setSegment("일반");
			memberService.saveMember(m13);
			customerService.saveCustomer(c13, m13);
			
			Member m14 = new Member();
			m14.setMemberId("chanwoo037");
			m14.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m14.setPassword2(m14.getPassword());
			m14.setName("박찬우");
			m14.setPhone("010-8901-2345");
			m14.setEmail("chanwoo37@naver.com");
			m14.setGender("남성");
			m14.setJoinDate(LocalDateTime.of(2023, 6, 28, 0, 0));
			m14.setRole(Role.CUSTOMER);
			Address a14 = new Address();
			a14.setCountry("한국");
			a14.setZipcode("04835");
			a14.setBasicAddress("서울시 성동구 성수동 235");
			a14.setDetailAddress("성동빌라 301호");
			m14.setAddress(a14);
			Customer c14 = new Customer();
			c14.setMember(m14);
			c14.setPurchaseCount(3);
			c14.setSegment("vip");
			memberService.saveMember(m14);
			customerService.saveCustomer(c14, m14);
			
			Member m15 = new Member();
			m15.setMemberId("donghoon015");
			m15.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m15.setPassword2(m15.getPassword());
			m15.setName("이동훈");
			m15.setPhone("010-5678-9012");
			m15.setEmail("leedong15@naver.com");
			m15.setGender("남성");
			m15.setJoinDate(LocalDateTime.of(2024, 4, 8, 0, 0));
			m15.setRole(Role.CUSTOMER);
			Address a15 = new Address();
			a15.setCountry("한국");
			a15.setZipcode("62149");
			a15.setBasicAddress("강원도 원주시 혁신로 666");
			a15.setDetailAddress("힐스테이트 1203동 2904호");
			m15.setAddress(a15);
			Customer c15 = new Customer();
			c15.setMember(m15);
			c15.setPurchaseCount(1);
			c15.setSegment("일반");
			memberService.saveMember(m15);
			customerService.saveCustomer(c15, m15);
			
			Member m16 = new Member();
			m16.setMemberId("sangmi041");
			m16.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m16.setPassword2(m16.getPassword());
			m16.setName("이상미");
			m16.setPhone("010-2345-6789");
			m16.setEmail("leesangmi41@naver.com");
			m16.setGender("여성");
			m16.setJoinDate(LocalDateTime.of(2024, 9, 18, 0, 0));
			m16.setRole(Role.CUSTOMER);
			Address a16 = new Address();
			a16.setCountry("한국");
			a16.setZipcode("42149");
			a16.setBasicAddress("경기도 고양시 덕양구 용두로 562");
			a16.setDetailAddress("롯데캐슬 102동 911호");
			m16.setAddress(a16);
			Customer c16 = new Customer();
			c16.setMember(m16);
			c16.setPurchaseCount(1);
			c16.setSegment("일반");
			memberService.saveMember(m16);
			customerService.saveCustomer(c16, m16);
			
			Member m17 = new Member();
			m17.setMemberId("sanghoon024");
			m17.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m17.setPassword2(m17.getPassword());
			m17.setName("이상훈");
			m17.setPhone("010-4567-8901");
			m17.setEmail("leesang24@naver.com");
			m17.setGender("남성");
			m17.setJoinDate(LocalDateTime.of(2024, 9, 18, 0, 0));
			m17.setRole(Role.CUSTOMER);
			Address a17 = new Address();
			a17.setCountry("한국");
			a17.setZipcode("06219");
			a17.setBasicAddress("서울시 강서구 마곡로 122");
			a17.setDetailAddress("e편한세상 2102동 1908호");
			m17.setAddress(a17);
			Customer c17 = new Customer();
			c17.setMember(m17);
			c17.setPurchaseCount(1);
			c17.setSegment("일반");
			memberService.saveMember(m17);
			customerService.saveCustomer(c17, m17);
			
			Member m18 = new Member();
			m18.setMemberId("unji027");
			m18.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m18.setPassword2(m18.getPassword());
			m18.setName("이상희");
			m18.setPhone("010-2345-6789");
			m18.setEmail("leesang412@naver.com");
			m18.setGender("여성");
			m18.setJoinDate(LocalDateTime.of(2023, 4, 23, 0, 0));
			m18.setRole(Role.CUSTOMER);
			Address a18 = new Address();
			a18.setCountry("한국");
			a18.setZipcode("10448");
			a18.setBasicAddress("경기도 고양시 일산동구 일산로 555");
			a18.setDetailAddress("흰돌마을5단지아파트 101동 101호");
			m18.setAddress(a18);
			Customer c18 = new Customer();
			c18.setMember(m18);
			c18.setPurchaseCount(1);
			c18.setSegment("일반");
			memberService.saveMember(m18);
			customerService.saveCustomer(c18, m18);
			
			Member m19 = new Member();
			m19.setMemberId("shanghee012");
			m19.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m19.setPassword2(m19.getPassword());
			m19.setName("이은지");
			m19.setPhone("010-7890-1234");
			m19.setEmail("leeunji27@naver.com");
			m19.setGender("여성");
			m19.setJoinDate(LocalDateTime.of(2024, 7, 11, 0, 0));
			m19.setRole(Role.CUSTOMER);
			Address a19 = new Address();
			a19.setCountry("한국");
			a19.setZipcode("61758");
			a19.setBasicAddress("광주시 남구 고싸움로 18");
			a19.setDetailAddress("칠석빌라 303호");
			m19.setAddress(a19);
			Customer c19 = new Customer();
			c19.setMember(m19);
			c19.setPurchaseCount(0);
			c19.setSegment("일반");
			memberService.saveMember(m19);
			customerService.saveCustomer(c19, m19);
			
			Member m20 = new Member();
			m20.setMemberId("jeongmin019");
			m20.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m20.setPassword2(m20.getPassword());
			m20.setName("이정민");
			m20.setPhone("010-9012-3456");
			m20.setEmail("jmin209@naver.com");
			m20.setGender("남성");
			m20.setJoinDate(LocalDateTime.of(2025, 1, 24, 0, 0));
			m20.setRole(Role.CUSTOMER);
			Address a20 = new Address();
			a20.setCountry("한국");
			a20.setZipcode("57940");
			a20.setBasicAddress("전라남도 순천시 매곡새길 25");
			a20.setDetailAddress("매곡빌라 401호");
			m20.setAddress(a20);
			Customer c20 = new Customer();
			c20.setMember(m20);
			c20.setPurchaseCount(0);
			c20.setSegment("일반");
			memberService.saveMember(m20);
			customerService.saveCustomer(c20, m20);
			
			Member m21 = new Member();
			m21.setMemberId("jooho032");
			m21.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m21.setPassword2(m21.getPassword());
			m21.setName("이주호");
			m21.setPhone("010-2134-6155");
			m21.setEmail("leejooho32@naver.com");
			m21.setGender("남성");
			m21.setJoinDate(LocalDateTime.of(2024, 10, 29, 0, 0));
			m21.setRole(Role.CUSTOMER);
			Address a21 = new Address();
			a21.setCountry("한국");
			a21.setZipcode("48518");
			a21.setBasicAddress("부산시 남구 용호로 20");
			a21.setDetailAddress("롯데캐슬 1001동 1807호");
			m21.setAddress(a21);
			Customer c21 = new Customer();
			c21.setMember(m21);
			c21.setPurchaseCount(3);
			c21.setSegment("vip");
			memberService.saveMember(m21);
			customerService.saveCustomer(c21, m21);
			
			Member m22 = new Member();
			m22.setMemberId("haneul036");
			m22.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m22.setPassword2(m22.getPassword());
			m22.setName("이하늘");
			m22.setPhone("010-7890-1234");
			m22.setEmail("leehaneul36@naver.com");
			m22.setGender("남성");
			m22.setJoinDate(LocalDateTime.of(2024, 9, 22, 0, 0));
			m22.setRole(Role.CUSTOMER);
			Address a22 = new Address();
			a22.setCountry("한국");
			a22.setZipcode("49230");
			a22.setBasicAddress("부산시 남구 수영로 567");
			a22.setDetailAddress("더샵 205동 801호");
			m22.setAddress(a22);
			Customer c22 = new Customer();
			c22.setMember(m22);
			c22.setPurchaseCount(0);
			c22.setSegment("일반");
			memberService.saveMember(m22);
			customerService.saveCustomer(c22, m22);
			
			Member m23 = new Member();
			m23.setMemberId("heonjoo007");
			m23.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m23.setPassword2(m23.getPassword());
			m23.setName("이현주");
			m23.setPhone("010-7890-4754");
			m23.setEmail("leeheonjoo7@naver.com");
			m23.setGender("여성");
			m23.setJoinDate(LocalDateTime.of(2025, 2, 3, 0, 0));
			m23.setRole(Role.CUSTOMER);
			Address a23 = new Address();
			a23.setCountry("한국");
			a23.setZipcode("32195");
			a23.setBasicAddress("대전시 유성구 계룡로 789");
			a23.setDetailAddress("유성빌라 202호");
			m23.setAddress(a23);
			Customer c23 = new Customer();
			c23.setMember(m23);
			c23.setPurchaseCount(2);
			c23.setSegment("일반");
			memberService.saveMember(m23);
			customerService.saveCustomer(c23, m23);
			
			Member m24 = new Member();
			m24.setMemberId("mirae029");
			m24.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m24.setPassword2(m24.getPassword());
			m24.setName("정미래");
			m24.setPhone("010-9012-3456");
			m24.setEmail("mirae29@naver.com");
			m24.setGender("여성");
			m24.setJoinDate(LocalDateTime.of(2024, 2, 25, 0, 0));
			m24.setRole(Role.CUSTOMER);
			Address a24 = new Address();
			a24.setCountry("한국");
			a24.setZipcode("30921");
			a24.setBasicAddress("대전시 대덕구 대덕로 321");
			a24.setDetailAddress("대덕아파트 103동 1301호");
			m24.setAddress(a24);
			Customer c24 = new Customer();
			c24.setMember(m24);
			c24.setPurchaseCount(1);
			c24.setSegment("일반");
			memberService.saveMember(m24);
			customerService.saveCustomer(c24, m24);
			
			Member m25 = new Member();
			m25.setMemberId("miseon018");
			m25.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m25.setPassword2(m25.getPassword());
			m25.setName("정미선");
			m25.setPhone("010-8901-2345");
			m25.setEmail("miseon18@naver.com");
			m25.setGender("여성");
			m25.setJoinDate(LocalDateTime.of(2023, 6, 4, 0, 0));
			m25.setRole(Role.CUSTOMER);
			Address a25 = new Address();
			a25.setCountry("한국");
			a25.setZipcode("19218");
			a25.setBasicAddress("경기도 수원시 영통구 영통로 123");
			a25.setDetailAddress("영통빌라 404호");
			m25.setAddress(a25);
			Customer c25 = new Customer();
			c25.setMember(m25);
			c25.setPurchaseCount(1);
			c25.setSegment("일반");
			memberService.saveMember(m25);
			customerService.saveCustomer(c25, m25);
			
			Member m26 = new Member();
			m26.setMemberId("sanghoon038");
			m26.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m26.setPassword2(m26.getPassword());
			m26.setName("정상훈");
			m26.setPhone("010-9012-3456");
			m26.setEmail("sanghoon38@naver.com");
			m26.setGender("남성");
			m26.setJoinDate(LocalDateTime.of(2024, 8, 17, 0, 0));
			m26.setRole(Role.CUSTOMER);
			Address a26 = new Address();
			a26.setCountry("한국");
			a26.setZipcode("20614");
			a26.setBasicAddress("경기도 시흥시 시화로 898");
			a26.setDetailAddress("시화아파트 808동 808호");
			m26.setAddress(a26);
			Customer c26 = new Customer();
			c26.setMember(m26);
			c26.setPurchaseCount(1);
			c26.setSegment("일반");
			memberService.saveMember(m26);
			customerService.saveCustomer(c26, m26);
			
			Member m27 = new Member();
			m27.setMemberId("youngcheol025");
			m27.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m27.setPassword2(m27.getPassword());
			m27.setName("정영철");
			m27.setPhone("010-5678-9012");
			m27.setEmail("youngcheol25@naver.com");
			m27.setGender("남성");
			m27.setJoinDate(LocalDateTime.of(2025, 4, 10, 0, 0));
			m27.setRole(Role.CUSTOMER);
			Address a27 = new Address();
			a27.setCountry("한국");
			a27.setZipcode("21480");
			a27.setBasicAddress("경기도 고양시 덕양구 덕양로 350");
			a27.setDetailAddress("덕양아파트 202동 1501호");
			m27.setAddress(a27);
			Customer c27 = new Customer();
			c27.setMember(m27);
			c27.setPurchaseCount(0);
			c27.setSegment("일반");
			memberService.saveMember(m27);
			customerService.saveCustomer(c27, m27);
			
			Member m28 = new Member();
			m28.setMemberId("eunhye100");
			m28.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m28.setPassword2(m28.getPassword());
			m28.setName("정은혜");
			m28.setPhone("010-5678-8765");
			m28.setEmail("eunhye100@naver.com");
			m28.setGender("여성");
			m28.setJoinDate(LocalDateTime.of(2024, 7, 12, 0, 0));
			m28.setRole(Role.CUSTOMER);
			Address a28 = new Address();
			a28.setCountry("한국");
			a28.setZipcode("01384");
			a28.setBasicAddress("서울시 강서구 공항대로 123");
			a28.setDetailAddress("공항빌라 103호");
			m28.setAddress(a28);
			Customer c28 = new Customer();
			c28.setMember(m28);
			c28.setPurchaseCount(0);
			c28.setSegment("일반");
			memberService.saveMember(m28);
			customerService.saveCustomer(c28, m28);
			
			Member m29 = new Member();
			m29.setMemberId("jiwoo014");
			m29.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m29.setPassword2(m29.getPassword());
			m29.setName("정지우");
			m29.setPhone("010-4567-8901");
			m29.setEmail("jiwoo14@naver.com");
			m29.setGender("남성");
			m29.setJoinDate(LocalDateTime.of(2024, 9, 17, 0, 0));
			m29.setRole(Role.CUSTOMER);
			Address a29 = new Address();
			a29.setCountry("한국");
			a29.setZipcode("50183");
			a29.setBasicAddress("충청남도 천안시 동남구 충절로 555");
			a29.setDetailAddress("동남빌라 101동 103호");
			m29.setAddress(a29);
			Customer c29 = new Customer();
			c29.setMember(m29);
			c29.setPurchaseCount(1);
			c29.setSegment("일반");
			memberService.saveMember(m29);
			customerService.saveCustomer(c29, m29);
			
			Member m30 = new Member();
			m30.setMemberId("jimin009");
			m30.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m30.setPassword2(m30.getPassword());
			m30.setName("홍지민");
			m30.setPhone("010-9012-3456");
			m30.setEmail("jimin9@naver.com");
			m30.setGender("여성");
			m30.setJoinDate(LocalDateTime.of(2025, 9, 17, 0, 0));
			m30.setRole(Role.CUSTOMER);
			Address a30 = new Address();
			a30.setCountry("한국");
			a30.setZipcode("18416");
			a30.setBasicAddress("경기도 성남시 분당구 정자동 876");
			a30.setDetailAddress("장미아파트 1201동 1401호");
			m30.setAddress(a30);
			Customer c30 = new Customer();
			c30.setMember(m30);
			c30.setPurchaseCount(1);
			c30.setSegment("일반");
			memberService.saveMember(m30);
			customerService.saveCustomer(c30, m30);
			
			Member m31 = new Member();
			m31.setMemberId("taesu010");
			m31.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m31.setPassword2(m31.getPassword());
			m31.setName("정태수");
			m31.setPhone("010-0123-4567");
			m31.setEmail("taesu10@naver.com");
			m31.setGender("남성");
			m31.setJoinDate(LocalDateTime.of(2024, 11, 18, 0, 0));
			m31.setRole(Role.CUSTOMER);
			Address a31 = new Address();
			a31.setCountry("한국");
			a31.setZipcode("01375");
			a31.setBasicAddress("서울시 마포구 월드컵로 987");
			a31.setDetailAddress("마포빌라 502호");
			m31.setAddress(a31);
			Customer c31 = new Customer();
			c31.setMember(m31);
			c31.setPurchaseCount(1);
			c31.setSegment("일반");
			memberService.saveMember(m31);
			customerService.saveCustomer(c31, m31);
			
			Member m32 = new Member();
			m32.setMemberId("hayun005");
			m32.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m32.setPassword2(m32.getPassword());
			m32.setName("정하윤");
			m32.setPhone("010-5678-9012");
			m32.setEmail("hayun5@naver.com");
			m32.setGender("여성");
			m32.setJoinDate(LocalDateTime.of(2024, 11, 11, 0, 0));
			m32.setRole(Role.CUSTOMER);
			Address a32 = new Address();
			a32.setCountry("한국");
			a32.setZipcode("56841");
			a32.setBasicAddress("광주시 북구 용봉로 321");
			a32.setDetailAddress("용봉아파트 2101동 2007호");
			m32.setAddress(a32);
			Customer c32 = new Customer();
			c32.setMember(m32);
			c32.setPurchaseCount(2);
			c32.setSegment("일반");
			memberService.saveMember(m32);
			customerService.saveCustomer(c32, m32);
			
			Member m33 = new Member();
			m33.setMemberId("kangho017");
			m33.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m33.setPassword2(m33.getPassword());
			m33.setName("최강호");
			m33.setPhone("010-7890-1234");
			m33.setEmail("kangho17@naver.com");
			m33.setGender("남성");
			m33.setJoinDate(LocalDateTime.of(2025, 10, 22, 0, 0));
			m33.setRole(Role.CUSTOMER);
			Address a33 = new Address();
			a33.setCountry("한국");
			a33.setZipcode("01644");
			a33.setBasicAddress("서울시 구로구 디지털로 100");
			a33.setDetailAddress("구로오피스텔 811호");
			m33.setAddress(a33);
			Customer c33 = new Customer();
			c33.setMember(m33);
			c33.setPurchaseCount(0);
			c33.setSegment("일반");
			memberService.saveMember(m33);
			customerService.saveCustomer(c33, m33);
			
			Member m34 = new Member();
			m34.setMemberId("minwoo013");
			m34.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m34.setPassword2(m34.getPassword());
			m34.setName("최민우");
			m34.setPhone("010-3456-7890");
			m34.setEmail("minwoo13@naver.com");
			m34.setGender("남성");
			m34.setJoinDate(LocalDateTime.of(2024, 5, 11, 0, 0));
			m34.setRole(Role.CUSTOMER);
			Address a34 = new Address();
			a34.setCountry("한국");
			a34.setZipcode("53419");
			a34.setBasicAddress("전라북도 전주시 완산구 서신로 444");
			a34.setDetailAddress("완산아파트 102동 404호");
			m34.setAddress(a34);
			Customer c34 = new Customer();
			c34.setMember(m34);
			c34.setPurchaseCount(1);
			c34.setSegment("일반");
			memberService.saveMember(m34);
			customerService.saveCustomer(c34, m34);
			
			Member m35 = new Member();
			m35.setMemberId("yeongho023");
			m35.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m35.setPassword2(m35.getPassword());
			m35.setName("최영호");
			m35.setPhone("010-3456-7890");
			m35.setEmail("yeongho23@naver.com");
			m35.setGender("남성");
			m35.setJoinDate(LocalDateTime.of(2025, 1, 2, 0, 0));
			m35.setRole(Role.CUSTOMER);
			Address a35 = new Address();
			a35.setCountry("한국");
			a35.setZipcode("53419");
			a35.setBasicAddress("울산시 중구 울산로 5334");
			a35.setDetailAddress("울산빌라 203호");
			m35.setAddress(a35);
			Customer c35 = new Customer();
			c35.setMember(m35);
			c35.setPurchaseCount(1);
			c35.setSegment("일반");
			memberService.saveMember(m35);
			customerService.saveCustomer(c35, m35);
			
			
			/*
			 * ------------------------------------------------------------------------------------------------------
			 */
			Member m36 = new Member();
			m36.setMemberId("aaaa");
			m36.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m36.setPassword2(m36.getPassword());
			m36.setName("이익준");
			m36.setPhone("010-2345-3215");
			m36.setEmail("aaaa@naver.com");
			m36.setGender("남성");
			m36.setJoinDate(LocalDateTime.now());
			m36.setRole(Role.CUSTOMER);
			Address a36 = new Address();
			a36.setCountry("한국");
			a36.setZipcode("07378");
			a36.setBasicAddress("서울 영등포구 가마산로 313");
			a36.setDetailAddress("가마빌라 222호");
			m36.setAddress(a36);
			Customer c36 = new Customer();
			c36.setMember(m36);
			c36.setPurchaseCount(0);
			c36.setSegment("일반");
			memberService.saveMember(m36);
			customerService.saveCustomer(c36, m36);
			
			Member m37 = new Member();
			m37.setMemberId("bbbb");
			m37.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m37.setPassword2(m37.getPassword());
			m37.setName("채송화");
			m37.setPhone("010-2350-9184");
			m37.setEmail("bbbb@naver.com");
			m37.setGender("여성");
			m37.setJoinDate(LocalDateTime.now());
			m37.setRole(Role.CUSTOMER);
			Address a37 = new Address();
			a37.setCountry("한국");
			a37.setZipcode("01237");
			a37.setBasicAddress("서울 강북구 월계로 53");
			a37.setDetailAddress("월계아파트 333호");
			m37.setAddress(a37);
			Customer c37 = new Customer();
			c37.setMember(m37);
			c37.setPurchaseCount(1);
			c37.setSegment("일반");
			memberService.saveMember(m37);
			customerService.saveCustomer(c37, m37);
			
			Member m38 = new Member();
			m38.setMemberId("cccc");
			m38.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m38.setPassword2(m38.getPassword());
			m38.setName("김준완");
			m38.setPhone("010-1230-9207");
			m38.setEmail("cccc@naver.com");
			m38.setGender("남성");
			m38.setJoinDate(LocalDateTime.now());
			m38.setRole(Role.CUSTOMER);
			Address a38 = new Address();
			a38.setCountry("한국");
			a38.setZipcode("04767");
			a38.setBasicAddress("서울 성동구 광나루로 104");
			a38.setDetailAddress("성동아파트 111호");
			m38.setAddress(a38);
			Customer c38 = new Customer();
			c38.setMember(m38);
			c38.setPurchaseCount(2);
			c38.setSegment("일반");
			memberService.saveMember(m38);
			customerService.saveCustomer(c38, m38);
			
			Member m39 = new Member();
			m39.setMemberId("dddd");
			m39.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m39.setPassword2(m39.getPassword());
			m39.setName("양석형");
			m39.setPhone("010-8327-1180");
			m39.setEmail("dddd@naver.com");
			m39.setGender("남성");
			m39.setJoinDate(LocalDateTime.now());
			m39.setRole(Role.CUSTOMER);
			Address a39 = new Address();
			a39.setCountry("한국");
			a39.setZipcode("22229");
			a39.setBasicAddress("인천 미추홀구 경원대로 715");
			a39.setDetailAddress("미추빌라 444호");
			m39.setAddress(a39);
			Customer c39 = new Customer();
			c39.setMember(m39);
			c39.setPurchaseCount(3);
			c39.setSegment("vip");
			memberService.saveMember(m39);
			customerService.saveCustomer(c39, m39);
			
			Member m40 = new Member();
			m40.setMemberId("customer");
			m40.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m40.setPassword2(m40.getPassword());
			m40.setName("고객");
			m40.setPhone("010-0001-0000");
			m40.setEmail("cusotmer@naver.com");
			m40.setGender("남성");
			m40.setJoinDate(LocalDateTime.now());
			m40.setRole(Role.CUSTOMER);
			Address a40 = new Address();
			a40.setCountry("한국");
			a40.setZipcode("06373");
			a40.setBasicAddress("서울특별시 강남구 자곡동 631");
			a40.setDetailAddress("강남타워 222동 1012호");
			m40.setAddress(a40);
			Customer c40 = new Customer();
			c40.setMember(m40);
			c40.setPurchaseCount(4);
			c40.setSegment("일반");
			memberService.saveMember(m40);
			customerService.saveCustomer(c40, m40);
			
			Member m41 = new Member();
			m41.setMemberId("faker");
			m41.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m41.setPassword2(m41.getPassword());
			m41.setName("이상혁");
			m41.setPhone("010-0344-1026");
			m41.setEmail("faker@naver.com");
			m41.setGender("남성");
			m41.setJoinDate(LocalDateTime.now());
			m41.setRole(Role.CUSTOMER);
			Address a41 = new Address();
			a41.setCountry("한국");
			a41.setZipcode("06100");
			a41.setBasicAddress("서울특별시 강남구 선릉로 627");
			a41.setDetailAddress("신사아파트 313동 2012호");
			m41.setAddress(a41);
			Customer c41 = new Customer();
			c40.setMember(m41);
			c40.setPurchaseCount(0);
			c40.setSegment("일반");
			memberService.saveMember(m41);
			customerService.saveCustomer(c41, m41);
			
			Member m42 = new Member();
			m42.setMemberId("dealer");
			m42.setPassword(new BCryptPasswordEncoder().encode("1234")); // 비밀번호 암호화
			m42.setPassword2(m42.getPassword());
			m42.setName("딜러");
			m42.setPhone("010-0002-0021");
			m42.setEmail("dealer@naver.com");
			m42.setGender("남성");
			m42.setJoinDate(LocalDateTime.now());
			m42.setRole(Role.DEALER);
			Address a42 = new Address();
			a42.setCountry("한국");
			a42.setZipcode("06035");
			a42.setBasicAddress("서울특별시 강남구 신사동 537-5");
			a42.setDetailAddress("신사아파트 313동 2012호");
			m42.setAddress(a42);
			Dealer dd1 = new Dealer();
			memberService.saveMember(m42);
			dealerService.saveDealer(dd1, m42);			
			
		};
	}
}
