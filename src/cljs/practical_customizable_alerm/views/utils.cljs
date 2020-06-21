(ns practical-customizable-alerm.views.utils
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [practical-customizable-alerm.events :as events]
            [practical-customizable-alerm.subs :as subs]))

(defn section [title subillust contents link]
  [:section.section
   [:div.container
    title
    (if subillust
      [:div.column>div.columns
       [:div.column.is-three-fifths
        [:div.container
         contents
         (if link
           [:button.link-button
            [:a {:href (:url link)}
             (:text link)]])]]
       [:div.column.subillust
        subillust]]
      [:div.column>div.columns>div.column
       [:div.container
        contents
        (if link
          [:button.link-button
           [:a {:href (:url link)}
            (:text link)]])]])]])

(defn section-handler [section-item]
  (section (:title section-item) (:subillust section-item) (:contents section-item) (:link section-item)))

(defn panel [id title body]
  (let [state (r/atom {})
        contents
        [:div.accordion-content
         [:div.container
          body]]]
    (fn [id title body]
      (let [open? (get @(rf/subscribe [::subs/accordion-toggle]) id false)
            child-height (:child-height @state)]
        [:article.message
         [:div.message-header
          (when open? {:style {:background-color "#a3a3a3"}})
          [:div.accordion-header.toggle
           {:on-click
            (fn []
              (rf/dispatch [::events/toggle-accordion id]))}
           title]]
         [:div>div.accordion-body
          {:style
           {:overflow "hidden"
            :transition "max-height 0.25s"
            :padding  0
            :max-height (if open? child-height 0)}}
          [:div.panel-body {:ref #(when %
                         (swap! state assoc :child-height (.-clientHeight %)))
                 :style {:padding "2rem"}}
           contents]]
         ]))))
