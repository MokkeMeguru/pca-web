(ns practical-customizable-alerm.core
  (:require
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [practical-customizable-alerm.events :as events]
   [practical-customizable-alerm.views :as views]
   [practical-customizable-alerm.config :as config]
   [practical-customizable-alerm.routes :as routes]
   [practical-customizable-alerm.idb :as idb]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (routes/init-routes!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))



(defn init []
  (let [request (-> js/window
                    .-indexedDB
                    (.open idb/dbname 3))]
   (re-frame/dispatch-sync [::events/initialize-db])
   (dev-setup)
   (mount-root)
   (set! (.-onerror request) idb/idb-request-error)
   (set! (.-onsuccess request) idb/idb-request-success)
   (set! (.-onupgradeneeded request)
         #(let [db (.. % -target -result)]
            (println "test" db)
            (re-frame/dispatch-sync [::events/init-indexed-db db])
            (idb/create-object-store db idb/sound-table)
            (idb/create-object-store db idb/alarm-table)))))
