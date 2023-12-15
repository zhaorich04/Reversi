package cs3500.reversi.view;

import java.util.ArrayList;

/**
 * Listens for events in the view.
 */
public class ReversiViewListeners implements ViewListeners {
  private ArrayList<ViewFeatures> featuresList;

  /**
   * Constructs a ReversiViewListeners.
   */
  ReversiViewListeners() {
    this.featuresList = new ArrayList<>();
  }

  /**
   * adds viewFeatures to the features list field.
   * @param features the view features to be added to the list.
   */
  @Override
  public void addViewListerners(ViewFeatures features) {
    featuresList.add(features);
  }

}
