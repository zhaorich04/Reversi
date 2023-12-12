package cs3500.reversi.model;

/**
 * Interface representing the model features that notify about model updates.
 */
public interface ModelFeatures {

  void notifyAction();

  void addListener(ModelListeners listeners);

}
