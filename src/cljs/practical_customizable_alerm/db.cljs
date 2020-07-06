(ns practical-customizable-alerm.db
  (:require [reagent.core :as r]))

(def default-db
  {:name "re-frame"
   :accordion {}
   :shop-modals {}
   :viewing-shop "model"
   :viewing-setting "alarm"
   :raspihost ""
   :sounds []
   :indexed-db (r/atom {})
   })
