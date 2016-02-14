
<!DOCTYPE html>

<html>
    <head>
        <title>Word Guessing Game</title>
        <style>
            body{
                margin: 0;
                background: lightcyan;
                font-family: sans-serif;
            }
            game{
                border-collapse:collapse;
            }
            p    {color:darkblue;}

            header{
                background-color: darkslateblue;
                color:white;
                text-align:center;
                padding:5px;
            }



            footer {
                background-color: darkslateblue;
                color:white;
                clear:both;
                text-align:center;
                padding:5px;	
                float: end;
            }
            main {
                padding:10px;
                text-align: center;
                alignment-adjust: central;
                background-color: lightcyan;
                height: 100%;


            }
            button{
                width: 160;
            }



            section {
                padding:10px;
                text-align: center;
                alignment-adjust: central;
                background-color: lightcyan;
                color: black;
                height: 100%;
            }
            h1.guess{
                color:black;
            }
            nav {
                line-height:30px;
                background-color:#eeeeee;
                height:800px;
                width:180px;
                float:left;
                padding:5px;	      
            }
            table{
                height: 250px;
                width: 600px;
                border:  1 px solid black;
                border-collapse: collapse;
                padding: 10px;
                margin: 0 auto;
            }
            tr,td,th{
                border:none;
                padding: 10 px;
                background-color: lightgray;
                font-size: large;
                font-family: sans-serif;

            }
            div{
                padding: 4px;
                background-color: black;
                color: white;

            }
            input{
                width: 200px;
                height: 40px;
                font-size: large;
                border: 2px black;
                
            }
            button{
                width: 200px;
                height: 40px;
                font-size: large; 
            }


        </style>
    </head>
    <body>
        <header><h1>Welcome to the Wordguessing Game</h1></header>
        <nav>
            Easy Dictionary<br>
            Medium Dictionary<br>
            Hard Dictionary
        </nav>
        <% String wordToGuess = (String) session.getValue("control.list");
            String valueFromPost = (String) session.getValue("control.test");
            Boolean checkGuess = (Boolean) session.getValue("control.guess");
            Boolean lookUp = (Boolean) session.getValue("control.lookUp");
            String correct = (String) session.getValue("control.correct");
        %>
        <main>
            <form name="wordGuess" action="wordguess" method="POST">
                <table id="game" border="1" border-collapse="true">
                    <thead>
                        <tr>
                            <th colspan="3"> The guessing can begin now! </td>

                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>English word:</td>
                            <td><input type="text" name="word" <%if (wordToGuess != null) {%>value="<%=wordToGuess%>"<%} else {%>Value="Press Next Word!"}<%}%>
                                       /></td>
                            <td>
                                <input type="submit" name="do_that" value="Next Word"/><br>
                            </td>

                        </tr>
                        <tr>
                            <td>Danish word:</td>
                            <td>            <input type="text" name="guess" value="">
                            </td>
                            <td>
                                <input type="submit" name="do_that" value="Guess Word"/><br>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <input type="submit" name="do_that" value="Look Up Word"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </main>
        <section>
            <% if (lookUp == null || lookUp == false) {%>
            <% if (checkGuess != null && checkGuess == true) {%>
            <h1>Congratulations, <%=valueFromPost%>, is the right answer</h1>
            <% } else if (checkGuess != null && checkGuess == false) {%>
            <h1>Sorry! <%=valueFromPost%>, is not the right answer!</h1>
            <%} else {%>
            <h1>Try to guess, or Press Next Word to get a new word!</h1>
            <%}
            } else{%><h1>The answer is:<%=correct%>.</h1><%}%>



        </section>

        <footer>Here is my footer. Isn't it pretty?</footer>




    </body>
</html>
