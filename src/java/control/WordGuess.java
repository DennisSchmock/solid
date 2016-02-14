/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.WordPair;
import model.WordPairsLogic;

/**
 *
 * @author dennisschmock
 */
@WebServlet(name = "WordGuess", urlPatterns = {"/wordguess"})
public class WordGuess extends HttpServlet {

    private WordPairsLogic wordPairs = new WordPairsLogic();
    //   private ArrayList<String> wordsTried = new ArrayList<>();
    private String wordToGuess = " ";
    private String valueFromPost;
    private boolean guessedCorrect;
    private boolean lookUp = false;
    
//Initializing and putting words in word
    public void init(ServletConfig conf) throws ServletException {
        wordPairs.getWordCollection().put("Horse", "Hest");
        wordPairs.getWordCollection().put("Goat", "Ged");
        wordPairs.getWordCollection().put("Cow", "Ko");
        wordPairs.getWordCollection().put("Mouse", "Mus");

        super.init(conf);
    }



      /**
     * Handles the HTTP <code>GET</code> method. Rewired it to send it to DoPost.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method. Takes the do_that value and returns the parameter.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        // ArrayList<WordPair> wordPair = (ArrayList<WordPair>) session.getAttribute("view.cart");
        String do_this = req.getParameter("do_that");
        lookUp = false;
        
        session.setAttribute("control.lookUp", lookUp);
        session.setAttribute("control.guess", null);

        if (do_this == null) {
            // session.setAttribute("view.list", bookList);
            forward(req, res, "/index.jsp");
            return;
        }

        if (do_this.equalsIgnoreCase("next word")) {
            getRandomWord(session, req, res);
        }
        if (do_this.equalsIgnoreCase("guess word")) {
            checkGuess(session, req, res);
        }
        if (do_this.equalsIgnoreCase("look up word")) {
            lookUpWord(session, req, res);
        }
    }

    //Using the getRandomQuestion in the WordPairLogic class to get a random question.
    public void getRandomWord(HttpSession session, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        this.wordToGuess = wordPairs.getRandomQuestion();
        session.setAttribute("control.list", wordToGuess);
        forward(req, res, "/index.jsp");
    }
    
    //Using the guesschecker in WordPairLogic to validate the answer.
    public void checkGuess(HttpSession session, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String originalWord = this.wordToGuess;

        String guessedWord = req.getParameter("guess");

        if (wordPairs.checkGuess(originalWord, guessedWord)) {
            guessedCorrect = true;
        } else {
            guessedCorrect = false;
        }
        session.setAttribute("control.guess", guessedCorrect);
        session.setAttribute("control.test", guessedWord);
        forward(req, res, "/index.jsp");

    }
    
    //Using the lookUpWord method in WordPairLogic to find the correct answer. 
    public void lookUpWord(HttpSession session, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String correctWord = "";
        String wordFromForm = req.getParameter("word");
        lookUp = true;

        if (this.wordToGuess != null) {
             correctWord = wordPairs.lookup(wordFromForm);
        }

        session.setAttribute("control.lookUp", lookUp);
        session.setAttribute("control.correct", correctWord);
        forward(req, res, "/index.jsp");

    }
    
    
    public void forward(HttpServletRequest req, HttpServletResponse res, String path) throws IOException, ServletException {
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(path);
        rd.forward(req, res);
    }
}
