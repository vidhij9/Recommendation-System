package recommendationSystem.contentFilters.Service.Filters;

public class HybridFilter {

  /*

  1.  get input from user like on which filters he wants to get recommendations
  2.  based on users watchedList, Ratings and interestList

  ONE WAY
  ->  get list from search based filter
  ->  get lists from content and collaborative filter

  -> combine all those and score each and every movie based on ranking in these lists
  -> return list of movies with the highest scores in a ranked order


  OTHER WAY
  ->  build a list of keywords what a user wants and combine it with the users having similar interests
  ->  filter out top 'k' keywords
  ->  now go and look for movies in movie db
  ->  and combine it with lists from content and collaborative lists
  ->  score and return

   */
}
