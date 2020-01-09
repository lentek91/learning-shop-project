import {html, PolymerElement} from "@polymer/polymer/polymer-element";

class ShopBottomMenu extends PolymerElement{

  static get template() {
    return html`
      <vaadin-horizontal-layout>
        <vaadin-button id="save-btn">Save</vaadin-button>
        <vaadin-button id="cancel-btn">Cancel</vaadin-button>
      </vaadin-horizontal-layout>
    `;
  }
}

window.customElements.define('shop-bottom-menu', ShopBottomMenu);