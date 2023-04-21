import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_LISTING_URL = "http://localhost:8080/item";


export const deleteItemFromShoppingList = async (listingDeletionDTO, suggestion) => {
  return await axios.delete(`${BASE_LISTING_URL}/shopping/delete?suggestion=${suggestion}`, {
    data: listingDeletionDTO,
    headers: {
      Authorization: `Bearer ${await SessionToken()}`,
    },
  });
};

export const addItemToShoppingList = async (listingAdditionDTO, fridgeId, suggestion) => {
  return await axios.post(`${BASE_LISTING_URL}/shopping/add?fridgeId=${fridgeId}&suggestion=${suggestion}`, listingAdditionDTO, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`,
    },
  });
};

export const getItemsFromShoppingList = async (fridgeId) => {
  return await axios.get(`${BASE_LISTING_URL}/shopping/get?fridgeId=${fridgeId}`, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`,
    },
  });
};


export const loadAllListings = async () => {
    return await axios.get(`${BASE_LISTING_URL}/load`)
}

export const loadListingsByCategoryId = async (categoryId) => {
    return await axios.get(`${BASE_LISTING_URL}/category/${categoryId}/load`)
}

export const loadListingByItemId = async(itemId) => {
    return await axios.get(`${BASE_LISTING_URL}/load/${itemId}`)
}


export const loadImagesByItemId = async(itemId) => {
    return await axios.get(`${BASE_LISTING_URL}/load/pictures/${itemId}`)
}

export const filterByFullDesc = async(searchTerm, categoryId) => {
    return await axios.get(`${BASE_LISTING_URL}/load/filter?term=${searchTerm}&categoryId=${categoryId}`)
}