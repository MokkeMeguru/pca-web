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
       [vutils/panel id title body])
     rhelp/help-list)
    [:div.container.my-5.mx-5>div.box
     [:div.columns
      [:p.sub-title {:style {:text-indent "2rem"}} "お問い合わせフォーム"]]
     [:form
      {:action "https://docs.google.com/forms/u/2/d/e/1FAIpQLSfqNnfEt8LqQ4tnqoNQGn-Kt8_XsmJ6CvPOPX9cacdPANtoMg/formResponse"}
      [:p.control.has-icons-left.has-icons-right.my-3
       [:input.input {:type "email" :placeholder "Email" :name "emailAddress" :required true}]
       [:span.icon.is-small.is-left>i.fas.fa-envelope]
       [:span.icon.is-small.is-right>i.fas.fa-check]
       ]
      [:div.field.my-3
       [:div.label "Message"]
       [:div.control
        [:textarea.textarea {:placeholder "Textarea"
                             :name "entry.1896257624"
                             :required true}]]]
      [:div.control>button.button.is-link {:type "submit" :name "button" :on-click #(println "test")} "Submit"]]
     ]]
    ])
