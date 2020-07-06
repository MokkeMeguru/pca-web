(ns practical-customizable-alerm.routes
  (:require
   [re-frame.core :as rf]
   [reitit.coercion.malli :as remalli]
   [reitit.frontend :as ref]
   [reitit.frontend.easy :as resy]
   [practical-customizable-alerm.config :as conf]
   [practical-customizable-alerm.events :as events]
   [practical-customizable-alerm.views.home :as vhome]
   [practical-customizable-alerm.views.about :as vabout]
   [practical-customizable-alerm.views.shop :as vshop]
    [practical-customizable-alerm.views.private :as vprivate]
   ))


;; Triggering navigation from events

(def routes
  ["/" ;; (str conf/hashrouter-base "/")
   [""
    {:name :routes/home
     :view vhome/page}]
   ["about"
    {:name :routes/about
     :view vabout/page
     :controllers
     [{:start (fn []
                (.log js/console "enter about"))}]}]
   ["private/"
    ["alarm"
     {:name :routes/alarm
      :view vprivate/page
      :controllers
      [{:start (fn []
                 (rf/dispatch [::events/viewing-setting "alarm"]))}]}]
    ["sound"
     {:name :routes/sound
      :view vprivate/page
      :controllers
      [{:start (fn []
                 (rf/dispatch [::events/viewing-setting "sound"]))}]}]]
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
                 (.scrollTo js/window 0 0)
                 (rf/dispatch [::events/viewing-shop "song"]))}]
      }]
    ["voice"
     {:name :routes/voices-shop
      
      :view vshop/page
      :controllers
      [{:start (fn []
                 (.scrollTo js/window 0 0)
                 (rf/dispatch [::events/viewing-shop "voice"]))}]
      }]]
   {:controllers
    [{:start
      (fn []
        (println "entering how")
        (.scrollTo js/window 0 0))}
     {:stop
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
  (resy/start!
   router
   on-navigate
   {:use-fragment
    true ;;false
    }))
