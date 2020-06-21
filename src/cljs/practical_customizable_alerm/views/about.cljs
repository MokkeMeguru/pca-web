(ns practical-customizable-alerm.views.about
  (:require [re-frame.core :as re-frame]
            [practical-customizable-alerm.views.utils :as vutils]
            [practical-customizable-alerm.resources.help :as rhelp]
            ["@creativebulma/bulma-collapsible" :as cbc]))


(defn page
  []
  [:div.bd-main.page
   [:div.bd-main-container.container
    (vutils/section-handler rhelp/help)
    (map
     (fn [{:keys [id title body]}] ^{:key id}
       [vutils/panel id title body ])
     rhelp/help-list)
    ]])
