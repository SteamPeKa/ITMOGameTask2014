package view;

import data.Publisher;
import model.GameModel;
import model.ScoreCounter;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 06.06.14
 * Time: 19:13
 */
public class ScoreView extends JLabel implements Publisher.Subscriber {

    private final GameModel model;


    public ScoreView(final GameModel model) {
        this.model = model;
        model.addSubscriber(this);
        eventHappened(Publisher.Event.SCORE_CHANGED);
        ScoreCounter.getInstance().addSubscriber(this);
        setFocusable(false);

    }

    @Override
    public void eventHappened(final Publisher.Event event) {
        if (event == Publisher.Event.SCORE_CHANGED) {
            final StringBuilder builder = new StringBuilder("<html>");
            for (final String str : ScoreCounter.getInstance().getScore()) {
                builder.append(str);
                builder.append("<br>");
            }
            builder.append("</html>");
            setText(builder.toString());
        }

    }
}
