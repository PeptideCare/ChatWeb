<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manner Matter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" rel="stylesheet">
</head>
<body>
   
   <div class="container">
       
       <div class="title">
          <a href="check_info.jsp"><i class="fas fa-user"></i></a>
          <span>M</span> <span>M</span>
       </div>
       
       <form action="">
           <input type="text" name="search" placeholder="채팅방 검색">
           <button>검색</button>
       </form>
       
       <div class="chat">
           <table>
               <th>No.</th>
               <th>Name</th>
               <th> </th>
               <tr>
                   <td>1</td>
                   <td>이름</td>
                   <td>입장</td>
               </tr>
               <tr>
                   <td>2</td>
                   <td>이름</td>
                   <td>입장</td>
               </tr>
               <tr>
                   <td>3</td>
                   <td>이름</td>
                   <td>입장</td>
               </tr>
               <tr>
                   <td>4</td>
                   <td>이름</td>
                   <td>입장</td>
               </tr>
               <tr>
                   <td>5</td>
                   <td>이름</td>
                   <td>입장</td>
               </tr>
           </table>
       </div>
       
       <a href="make_room.jsp"><button>방만들기</button></a>
       
   </div>
    
</body>
</html>