<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manner Matter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/make.room.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" rel="stylesheet">
</head>
<body>
   
   <div class="container">
       
       <div class="title">
            <a href="main.jsp"><i class="fas fa-backward"></i></a>
           <span>M</span> <span>M</span>
       </div>
       
       <div class="info">
           <form action="">
               <input type="text" name="room_name" placeholder="채팅방 이름">
               <input type="number" name="room_limit" placeholder="방 인원 설정">
               <button>완료</button>
           </form>
       </div>
   </div>
    
</body>
</html>