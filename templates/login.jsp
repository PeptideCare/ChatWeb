<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manner Matter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
   
   <div class="container">
       
       <div class="title">
           <p><span>M</span>anner <span>M</span>atter</p>
       </div>
       
       <div class="info">
           <form action="/login">
               <input type="text" name="member_id" placeholder="ID">
               <input type="password" name="member_pw" placeholder="PW"> 
               <button>로그인</button>
           </form>
           <a href="join.jsp"><button>회원가입</button></a>
       </div>
   </div>
    
</body>
</html>