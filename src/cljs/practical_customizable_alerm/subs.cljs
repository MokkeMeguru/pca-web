(ns practical-customizable-alerm.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :current-route
 (fn [db]
   (:current-route db)))


(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))


(re-frame/reg-sub
 ::accordion-toggle
 (fn [db]
   (:accordion db)))

(re-frame/reg-sub
 ::shop-modal
 (fn [db [_ idx]]
   (get (:shop-modals db) idx)))

(re-frame/reg-sub
 ::shop-modals
 (fn [db]
   (vals (:shop-modals db))))

(re-frame/reg-sub
 ::viewing-shop
 (fn [db]
   (:viewing-shop db)))
