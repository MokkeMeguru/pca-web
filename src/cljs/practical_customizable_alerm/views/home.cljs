(ns practical-customizable-alerm.views.home
  (:require [re-frame.core :as rf]
            [practical-customizable-alerm.config :as conf]
            [practical-customizable-alerm.resources.home :as rhome]
            [practical-customizable-alerm.resources.about :as rabout]
            [practical-customizable-alerm.resources.shop :as rshop]
            [practical-customizable-alerm.resources.contact :as rcontact]
            [practical-customizable-alerm.views.utils :as vutils]))

(defn page
  []
  [:div.container.page
   [:figure.image>img {:src (str conf/hashrouter-base
                               "img/back-ground.png")}]
   (vutils/section-handler rhome/abstruct)
   (vutils/section-handler rshop/shop)
   (vutils/section-handler rabout/about)
   (vutils/section-handler rcontact/contact)])

