(ns practical-customizable-alerm.views.shop
  (:require [re-frame.core :as rf]
            [practical-customizable-alerm.subs :as subs]
            [practical-customizable-alerm.events :as events]
            [practical-customizable-alerm.resources.shop :as rshop]
            [practical-customizable-alerm.views.utils :as vutils]
            ))

(def left-arrow
  [:svg {:viewbox "0 0 59 56", :fill "none", :xmlns "http://www.w3.org/2000/svg"}
   [:path {:d "M20.0836 11.75L58.3336 33.8336L20.0836 55.9173L20.0836 11.75Z", :fill "#C4C4C4"}]])

(def right-arrow
  [:svg {:width "59", :height "57", :viewbox "0 0 59 57", :fill "none", :xmlns "http://www.w3.org/2000/svg"}
   [:path {:d "M39.1247 12.1335L38.3539 56.294L0.49515 33.5462L39.1247 12.1335Z", :fill "#C4C4C4"}]])

(defn model-item [item-info modals]
  (let [open? (rf/subscribe [::subs/shop-modal (:id item-info)])]
    (fn []
      [:div.box
       {:key (:id item-info)
        :style {:max-width "unset"
                :display "inline-block"}}
       [:img {:on-click #(when-not (and (not open?) (zero? (apply + @modals)))
                           (rf/dispatch [::events/open-shop-modal! (:id item-info)]))
              :src (:img item-info)
              :style {:max-width "400px"
                      :display "inline-block"}}]
       (when @open?
         [:div.modal.is-active
          {:on-click #(rf/dispatch [::events/close-shop-modal! (:id item-info)])}
          [:div.modal-background
           {:style {:z-index -1}}]
          [:div.modal-content
           {:style  {:border-radius "0.5rem"}}
           [:div.card
            [:div.card-header
             [:p.card-header-title.sub-title (:title item-info)]]
            [:div.card-image.my-5 {:style {:display "block" :text-align "center"}}
             [:figure {:style {:align-item "center"}}
              [:img {:src (:img item-info)}]]]
            [:div.card-content
             [:div.media
              [:div.media-content
               [:p.sub-title (:detail-title item-info)]
               (:detail-description item-info)
               [:div.content
                (:link item-info)]]]]]]])])))

(defn model-item-flex-container []
  (let [modals (rf/subscribe [::subs/shop-modals])]
    [:div.container
     {:key "model-item"
      :style {:padding-bottom "7rem"}}
     [:h2.sub-title {:style {:text-indent "5rem"}} "アイテム"]
     [:div.columns
      [:div.column.is-1.subarrow
       {:style {:margin "auto"}}
       right-arrow]
      [:div.column.is-10.shop-image
       [:div.container.mx-5>div.columns.is-scrollable.is-fillwidth
        {:style
         {:overflow-y "hidden"
          :overflow-x "scroll"
          :white-space "nowrap"}}
        (map (fn [item-info] [model-item item-info modals]) rshop/shop-items)]]
      [:div.column.is-1.subarrow
       {:style {:margin "auto"}}
       left-arrow]]]))

(defn model-charactor-flex-container []
  [:div.bd-main.page
   [:div.bd-main-container.container
    [:h2.sub-title {:style {:text-indent "5rem" :margin-bottom "5rem"}} "キャラクター"]
    [:div.container.mx-5>div.columns.is-centered>div.column.is-10
     (map
      (fn [{:keys [id title body]}]
        ^{:key id}
        [vutils/panel id title body])
      rshop/character-list)]
    ]])


(defn shop-nav []
  (let [shop @(rf/subscribe [::subs/viewing-shop])]
    [:div.breadcrumb.is-centered {:aria-label "breadcrumbs"}
     [:ul
      (if (= shop "model")
        [:li.is-active>a.title {:href "/shops/model"} "3D モデル"]
        [:li>a.title {:href "/shops/model"} "3D モデル"])
      (if (= shop "song")
        [:li.is-active>a.title {:href "/shops/song"} "楽曲"]
        [:li>a.title {:href "/shops/song"} "楽曲"])
      (if (= shop "voice")
        [:li.is-active>a.title {:href "/shops/voice"} "音声"]
        [:li>a.title {:href "/shops/voice"} "音声"]
        )]]))

(defn shop-header []
  [:div.container.py-5.px-5
   [:div.py-5.px-5 [:h2.title "公式素材"]]])

(defn page []
  (let [viewing-shop (rf/subscribe [::subs/viewing-shop])]
   [:div.container.page
    [shop-nav]
    [shop-header]
    (when (= @viewing-shop "model")
      [:div.my-5
       [model-item-flex-container]
       [model-charactor-flex-container]])
    (when (= @viewing-shop "song")
      [:div.container
       [:div.columns>column.is-10.my-5
        {:style {:text-indent "5rem"}}
        [:p.sub-title "楽曲"]]
       [:div.columns.is-centered>div.column.is-10.my-5
        {:style {:text-indent 0}}
        (map
         (fn [{:keys [id title body]}]
           ^{:key id}
           [vutils/panel id title body])
         rshop/song-list)]])
    (when (= @viewing-shop "voice")
      [:div.container
       [:div.columns>column.is-10.my-5
        {:style {:text-indent "5rem"}}
        [:p.sub-title "音声"]]
       [:div.columns.is-centered>div.column.is-10.my-5
        {:style {:text-indent 0}}
        (map
         (fn [{:keys [id title body]}]
           ^{:key id}
           [vutils/panel id title body])
         rshop/voice-list)]])
    ]))
