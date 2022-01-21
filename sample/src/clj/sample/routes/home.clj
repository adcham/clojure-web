(ns sample.routes.home
  (:require
   [sample.layout :as layout]
   [sample.db.core :as db]
   [clojure.java.io :as io]
   [sample.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
    [struct.core :as stc]))

(defn home-page [request]
  (layout/render request "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page [request]
  (layout/render request "about.html"))

(def review-schema
    {:book_id  [stc/required stc/number-str]
     :reviewer [stc/required stc/string]
     :review   [stc/required stc/string]})

(defn validate-review
    [params]
    (first (stc/validate params review-schema)))

(defn save-review [{:keys [params]}]
    (if-let [errors (validate-review params)]
        (-> (response/found "/reviews")
            (assoc :flash (assoc params :errors errors)))
        (do
            (db/create-review! params)
            (response/found "/reviews"))))

(defn reviews-page [{:keys [flash] :as request}]
  (layout/render
      request
      "reviews.html"
      (merge
          {:items (db/get-reviews)}
          (select-keys flash [:book_id :review :reviewer :errors]))))

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

