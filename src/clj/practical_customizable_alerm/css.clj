(ns practical-customizable-alerm.css
  (:require [garden.def :refer [defstyles defkeyframes]]
            [garden.stylesheet :refer [at-media]]))

(defkeyframes fadeIn
  ["0%"
   {:opacity 0}]
  ["100%"
   {:opacity 1}])

(defstyles screen
  
  fadeIn

  [:body
   {:display "flex"
    :flex-direction "column"
    :min-height "100vh"
    :background-color "#f9f9f9"
    :font-family "'Hiragino Kaku Gothic Pro','ヒラギノ角ゴ Pro W3','メイリオ',Meiryo,'ＭＳ Ｐゴシック',sans-serif"
    ;; :font-size "calc(112.5% + 0.5vw)"
    }]
  [:ul
   :overflow-x "scroll"
   :width "auto"
   :position "absolute"]

  [:.page
   {:animation-name [fadeIn]
    :animation-duration "1s"}]
  [:.breadcrumb
   {:margin-top "10rem"
    :padding "1rem 0rem"
    :border-bottom "solid 0.3rem #dbdbdb"}]

  [:.title
   {:font-size "calc(200% + 0.5vw)"}]
  [:.sub-title
   {:font-size "calc(150% + 0.5vw)"}]
  [:.sec-title
   {:line-height 1.5
    :border-bottom "solid 0.5rem gray"
    :padding-right "5rem"}]

  [:.footer
   {:width "100%"
    :margin-top "auto"
    :padding-top "1rem"
    :padding-bottom "1rem"
    :border-top "0.2rem solid lightgray"
    :z-index 1}]

  [:.navbar-item
   {:display "flex"}]
  [:.navbar
   :.navbar-menu
   :.navbar-start
   :.navbar-end
   {:align-items "stretch"
    :display "flex"
    :padding "0"}]
  [:.navbar-menu
   {:flex-grow "1"
    :flex-shrink  "0"}]
  [:.navbar-start
   {:justify-content  "flex-start"
    :margin-right "auto"}]
  [:.navbar-end
   {:justify-content "flex-end"
    :margin-left  "auto"}]
  [:.navbar-item
   {:display "flex"
    :align-items "center"}]

  [:.start {:justify-content "flex-start"}]
  [:.center {:justify-content "flex-center"}]
  [:.end
   {:justify-content "flex-end"
    :margin-left "auto"}]

  [:.link-button:hover
   {:background-color "#9b9b9b"
    :box-shadow "0px 8px 8px rgba(0, 0, 0, 0.05)"}]
  [:.link-button ".link-button:hover::before" ".link-button:hover::after"
   {:transition "all 0.3s"}]
  [:.link-button
   ;;  Rectangle 2
   {:position "flex"
    ;; :width "279px"
    :background "#575757"
    ;; :border "1px solid #000000"
    :boxsizing "border-box"
    :box-shadow "0px 8px 8px rgba(0, 0, 0, 0.25)"
    :color "white"
    :border-radius "20px"
    :font-size "calc(112.5% + 0.5vw)"}
   [:a
    {:color "white"}]
   [:a:hover
    {:color "lightgray"}]]

  [:.modal
   {:animation "fadeIn"
    :animation-duration "0.2s"}]
  [:.modal-content ":.modal-content::before" ":.modal-content::after"
   {:transition "all 0.5s"}]

  [:.accordion-header
   {:text-indent "1rem"}]

  [:.panel-body
   [:h2 {:text-indent "1rem"}]
   [:p {:text-indent "3rem"
        :font-size "calc(112.5% + 0.5vw)"}]]

  [:.is-close
   {:max-height 0
    :overflow "hidden"
    :padding 0}]

  (at-media {:screen true
             :max-width "800px"}
            [:.subillust
             {:display "none"}])
  (at-media {:screen true
             :max-width "800px"}
            [:.subarrow
             {:display "none"}])
  (at-media {:screen true
             :max-width "800px"}
            [:.shop-image
             [:img
              {:max-height "100px"}]]))
