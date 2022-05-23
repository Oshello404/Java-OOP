/**
 * CSCI1130 Java Assignment 6 BoardGame Reversi
 * Aim: Practise subclassing, method overriding
 *      Learn from other subclass examples
 * 
 * I declare that the assignment here submitted is original
 * except for source material explicitly acknowledged,
 * and that the same or closely related material has not been
 * previously submitted for another course.
 * I also acknowledge that I am aware of University policy and
 * regulations on honesty in academic work, and of the disciplinary
 * guidelines and procedures applicable to breaches of such
 * policy and regulations, as contained in the website.
 *
 * University Guideline on Academic Honesty:
 *   http://www.cuhk.edu.hk/policy/academichonesty
 * Faculty of Engineering Guidelines to Academic Honesty:
 *   https://www.erg.cuhk.edu.hk/erg/AcademicHonesty
 *
 * Student Name:  Hans Nathanael Junoes
 * Student ID  :  1155174034
 * Date        :  13/12/2021
 */

package boardgame;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Reversi is a TurnBasedGame
 */
public class Reversi extends TurnBasedGame {
    
    public static final String BLANK = " ";
    String winner;
    int blackPiece = 0;
    int whitePiece = 0;
    int count = 1;
    int mustPasscounter = 0;
    boolean statement = false;


    /*** TO-DO: STUDENT'S WORK HERE ***/
    
    // retrieved from Connect4
    protected boolean isBlank(int x, int y)
    {
        return pieces[x][y].getText().equals(BLANK);
    }
    
    protected boolean isFriend(int x, int y)
    {
        return pieces[x][y].getText().equals(currentPlayer);
    }
    
    protected boolean isOpponent(int x, int y)
    {
        return pieces[x][y].getText().equals(getOpponent());
    }
    
    // simple constructor
    public Reversi(int xCount, int yCount){
        super(xCount, yCount, "BLACK", "WHITE");
        this.setTitle("Reversi"); 
    }
            
    // initialize the game Reversi
    @Override
    protected void initGame()
    {
        for (int y = 0; y < yCount; y++)
            for (int x = 0; x < xCount; x++)
                pieces[x][y].setText(" ");
        
        // initialize pieces at middle of board
        pieces[3][3].setText("WHITE");
        pieces[4][4].setText("WHITE");
        pieces[3][3].setBackground(Color.WHITE);
        pieces[4][4].setBackground(Color.WHITE);
        pieces[3][3].setEnabled(false);
        pieces[4][4].setEnabled(false);
        pieces[3][4].setText("BLACK");
        pieces[4][3].setText("BLACK");
        pieces[3][4].setBackground(Color.BLACK);
        pieces[4][3].setBackground(Color.BLACK);
        pieces[3][4].setEnabled(false);
        pieces[4][3].setEnabled(false);
    }
            

    @Override
    protected void gameAction(JButton triggeredButton, int x, int y)
    {  
        // firstly check if triggeredButton is a valid move by calling isvalidMove
        // DO NOT change turns if it is invalid move
        // triggeredButton is null so player can click on it again in the future
        if(isValidMove(x, y) == false){
            addLineToOutput("INVALID MOVE for " + currentPlayer + " at (" + x + ", " + y + ")");
            triggeredButton = null;
        }
        
        // if valid move, then do the following :
        else{
            triggeredButton.setEnabled(false);
            triggeredButton.setText(currentPlayer);
            pieces[x][y].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
            addLineToOutput(currentPlayer + " move at (" + x + ", " + y + ")");
        
            // change turns
            changeTurn();
        
            // mustPasscounter is used to measure 2 successive passes, in which game ends
            // initialize at zero always each successful turn
            // this calculation is done in checkEndGame
            mustPasscounter = 0;
            checkEndGame(x, y);
            
            if (gameEnded){
                // countPieces counts number of pieces and initializes String winner
                countPieces();
                addLineToOutput("Game ended!");
                JOptionPane.showMessageDialog(null, "Game ended!\nScore :\nBlack :  " + blackPiece + "\nWhite :  " + whitePiece + "\nWinner is : " + winner);
            }
        }
    }
         
    protected Boolean isValidMove(int x, int y){
        // initial statement is false
        statement = false;
        
        // there are 8 direction vectors that a piece can capture an opponent piece
        for (int deltaX = -1; deltaX <= 1; deltaX++){
            for (int deltaY = -1; deltaY <= 1; deltaY++){
                // skip trivial and illegal direction vector <0, 0>
                if (deltaX == 0 && deltaY == 0) continue; 
                // find a neighbouring opponent piece to start checking for valid moves
                count = 1;
                try {
                    // if found opponent, continue in same direction
                    while (isOpponent(x+count*deltaX, y+count*deltaY)){
                        count++;
                        // if find friend, then it is a valid move and return true
                        if(isFriend(x + count*deltaX, y + count*deltaY)){
                            // call colorPieces to change captured pieces to friendly pieces
                            // this continues for every direction of deltaX and deltaY in
                            // which it can capture a piece. so all captured pieces are changed
                            colorPieces(count, x, y, deltaX, deltaY);
                            statement = true;
                        }
                    }
                }
                catch (ArrayIndexOutOfBoundsException e)
                {} // use try-catch to simplify code in boundary checking
            }
        } 
        // default return, it is false
        return(statement);
    }
        
    // simply used to colorPieces when capturing them
    // here, 5 parameters are needed from isValidMove() : 
    // - count measures the amount of captured pieces
    // - x, y measures the starting point (the currentPlayer's piece)
    // deltaX and deltaY is the direction vector of the line of captured pieces
    protected void colorPieces(int count, int x, int y, int deltaX, int deltaY){
        for(int i = 1; i < count+1; i++){
            pieces[x+i*deltaX][y+i*deltaY].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
            pieces[x+i*deltaX][y+i*deltaY].setText(currentPlayer);
        }
    }
    
    // count the number of each of BLACK and WHITE pieces. called when gameEnded is true only
    protected void countPieces(){
        for (int x = 0; x < xCount; x++){
            for (int y = 0; y < yCount; y++){
                if(pieces[x][y].getText().equals("BLACK")){
                    blackPiece++;
                }
                else if(pieces[x][y].getText().equals("WHITE"))
                    whitePiece++;
            }
        }
        // assign the winning piece to String winner
        if(blackPiece > whitePiece){
            winner = "BLACK";
        }
        else if(whitePiece > blackPiece){
            winner = "WHITE";
        }
        else{
            winner = "DRAW";
        }
    }
    
    // nearly identical to isValidMove(), but it does not call colorPiece, just checks only
    // checks for all valid moves for all squares, returns FALSE after finding one possible move
    protected Boolean mustPass(int moveX, int moveY){
        for (int x = 0; x < xCount; x++){
            for (int y = 0; y < yCount; y++)
            {
                // skip non-blank pieces. 
                // starts from blank pieces instead of friendly pieces because as game goes on blank 
                // pieces are less and less, so saves memory by not checking every friendly piece
                // (likely to be a lot). this also makes greater intuitive sense
                if (!isBlank(x, y)) continue;
                
                for (int deltaX = -1; deltaX <= 1; deltaX++){
                    for (int deltaY = -1; deltaY <= 1; deltaY++){
                        // skip trivial and illegal direction vector <0, 0>
                        if (deltaX == 0 && deltaY == 0) continue; 
                        
                        // find a neighbouring opponent piece to start checking for valid moves
                        count = 1;
                        try {
                            // continue in one direction for every opponent found
                            while (isOpponent(x + count*deltaX, y + count*deltaY)){
                                count++;
                                // if find friend, then there is a valid move and return false
                                if(isFriend(x + count*deltaX, y + count*deltaY)){  
                                    return false;
                                }
                                else 
                                    continue;
                                }
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {} // use try-catch to simplify code in boundary checking
                    }
                }
            }
        }
        // default return is true, meaning if no valid moves are found then current player must pass
        return(true);
    }
    
    @Override
    protected boolean checkEndGame(int x, int y)
    {
        while(mustPass(x, y)){
            // simple message to indicate player must pass, then changes turn
            addLineToOutput("[!] " + currentPlayer + " MUST PASS!");
            changeTurn();
            // each consecutive pass is measured my mustPasscounter
            mustPasscounter++;
            // if greater than or equal to 2, then game must end
            if (mustPasscounter >= 2)
                {
                    gameEnded = true;
                    break;          
                }
        }   
        // default return is false
        return(gameEnded);
    }
    
    
    public static void main(String[] args)
    {
        Reversi reversi;
        reversi = new Reversi(8, 8);
        
        // TO-DO: run other classes, one by one
        System.out.println("You are running class Reversi");
        
        // TO-DO: study comment and code in other given classes
        
        // TO-DO: uncomment these two lines when your work is ready
          reversi.setLocation(400, 20);
          reversi.verbose = false;

        // the game has started and GUI thread will take over here
    }
}
