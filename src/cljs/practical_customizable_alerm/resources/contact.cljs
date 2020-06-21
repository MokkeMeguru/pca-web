(ns practical-customizable-alerm.resources.contact)

(def contact
  {:title [:h1.title>span.sec-title "Contatct Us"]
   :contents [:div.container.px-5.py--5
              [:p "Twitter:" [:a {:href "https://twitter.com/MeguruMokke" } "@MokkeMeguru"]]
              [:p "email: " [:a {:href "mailto:meguru.mokke@gmail.com"} "meguru.mokke@gmail.com"]]]})
