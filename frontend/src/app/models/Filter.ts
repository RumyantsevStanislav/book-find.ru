export interface Filter {
  header?: string
  showAll?: string
  releaseDateMin?: Date
  releaseDateMax?: Date
  singleParams?: Map<singleParamName, string>
  multiParams?: Map<multiParamName, string[]>
  //authors: Set<Author>
}

export enum singleParamName {
  TILE = "title",
  PAGE_SIZE = "s",
  PAGE_NUMBER = "p",
  MIN_ESTIMATION = "min_estimation",
  MAX_ESTIMATION = "max_estimation",
  MIN_RELEASE_DATE = "min_year",
  MAX_RELEASE_DATE = "max_year",
  SEARCH = "search",
}

export enum multiParamName {
  CATEGORY = "categories",
}

//todo add "views" for "most popular title"
