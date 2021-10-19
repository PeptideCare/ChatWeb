<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manner Matter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/join.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" rel="stylesheet">
</head>
<body>
   
   <div class="container">
       
       <div class="title">
            <a href="login.jsp"><i class="fas fa-backward"></i></a>
           <span>M</span> <span>M</span>
       </div>
       
       <div class="info">
           <form action="">
              <div class="check_id">
                   <input type="text" name="member_id" placeholder="ID">
                   <input type="submit" value="중복확인">
               </div>
               <input type="password" name="member_pw" placeholder="PW">
               <input type="text" name="member_name" placeholder="이름">
               <input type="text" name="member_sex" placeholder="성별 ex) 남,여">
               <input type="number" name="member_birth" placeholder="생년월일 ex) 010101">
               <div class="check_phone">
                   <input type="number" name="member_phone" placeholder="핸드폰번호">
                   <input type="submit" value="인증">
               </div>
               <input type="text" name="member_school" placeholder="학교명">
               <button>회원가입</button>
           </form>
       </div>
   </div>
    
</body>
</html>