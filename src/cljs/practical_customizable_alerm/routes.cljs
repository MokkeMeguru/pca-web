(ns practical-customizable-alerm.routes
  (:require
   [re-frame.core :as rf]
   [reitit.coercion.malli :as remalli]
   [reitit.frontend :as ref]
   [reitit.frontend.easy :as resy]
   [practical-customizable-alerm.events :as events]
   [practical-customizable-alerm.views.home :as vhome]
   [practical-customizable-alerm.views.about :as vabout]
   [practical-customizable-alerm.views.shop :as vshop]
   ))


;; Triggering navigation from events

(def routes
  ["/"
   [""
    {:name :routes/home
     :view vhome/page}]
   ["about"
    {:name :routes/about
     :view vabout/page
     }]
   ["shops/"
    ["model"
     {:name :routes/models-shop
      :view vshop/page
      :controllers
      [{:start (fn []
                 (rf/dispatch [::events/viewing-shop "model"]))}]
      }]
    ["song"
     {:name :routes/songs-shop
      :view vshop/page
      :controllers
      [{:start (fn []
                 (rf/dispatch [::events/viewing-shop "song"]))}]
      }]
    ["voice"
     {:name :routes/voices-shop
      
      :view vshop/page
      :controllers
      [{:start (fn []
                 (rf/dispatch [::events/viewing-shop "voice"]))}]
      }]]
   {:controllers
    [{:stop
      (fn []
        (rf/dispatch [::events/reset-accordion]))}]}])

(def router
  (ref/router
   routes
   {:data {:coercion remalli/coercion}}))

(defn on-navigate [new-match]
  (when new-match
    (rf/dispatch [:navigated new-match])))

(defn init-routes! []
  (js/console.log "initialize routes")
  (resy/start!
   router
   on-navigate
   {:use-fragment false}))
