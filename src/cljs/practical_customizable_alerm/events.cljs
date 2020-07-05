(ns practical-customizable-alerm.events
  (:require
   [re-frame.core :as re-frame]
   [practical-customizable-alerm.db :as db]
   [reitit.frontend.controllers :as refc]
   [reitit.frontend.easy :as resy]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-fx
 :navigate
 (fn [_cofx [_ & route]]
   {:navigate! route}))

(re-frame/reg-fx
 :navigate!
 (fn [route]
   (apply resy/push-state route)))


(re-frame/reg-event-db
 :navigated
 (fn [db [_ new-match]]
   (let [old-match (:current-route db)
         controllers (refc/apply-controllers (:controllers old-match) new-match)]
     (when-not (= new-match old-match) (.scrollTo js/window 0 0))
     (assoc db :current-route (assoc new-match :controllers controllers)))))


(re-frame/reg-event-db
 ::toggle-accordion
 (fn [db [_ id]]
   (update-in db [:accordion id] not)))

(re-frame/reg-event-db
 ::reset-accordion
 (fn [db _]
   (assoc db :accordion {})))

(re-frame/reg-event-db
 ::open-shop-modal!
 (fn [db [_ id]]
   (assoc-in db [:shop-modals id] true)))


(re-frame/reg-event-db
 ::close-shop-modal!
 (fn [db [_ id]]
   (assoc-in db [:shop-modals id] false)))



(re-frame/reg-event-db
 ::viewing-shop
 (fn [db [_ shop-id]]
   (println "ship" shop-id)
   (assoc db :viewing-shop shop-id)))

