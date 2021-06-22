package Controller;

import View.Home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController implements ActionListener {

    private final Home _viewHome;

    public HomeController(Home viewHome){

        _viewHome = viewHome;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == _viewHome.BooksB){

        }
        else{ //_viewHome.GamesB

        }
    }
}
