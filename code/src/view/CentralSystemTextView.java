package view;

import model.CentralSystemModel;

public class CentralSystemTextView implements CentralSystemView{

  CentralSystemModel model;

  public CentralSystemTextView(CentralSystemModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return this.model.toString();
  }
}
