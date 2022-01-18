(ns sample.routes.home
  (:require
   [sample.layout :as layout]
   [sample.db.core :as db]
   [clojure.java.io :as io]
   [sample.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]))

(defn home-page [request]
  (layout/render request "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page [request]
  (layout/render request "about.html"))

(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/about" {:get about-page}]
   ["/reviews" {:get reviews-page
                :post save-review}]])

(defn save-review [{:keys [params]}]
    (do
        (db/create-review! params)
        (response/found "/reviews")))

(defn reviews-page [request]
  (layout/render request "reviews.html" {:items (db/get-reviews)}))
