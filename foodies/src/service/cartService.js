import axios from "axios";

const API_URL = "http://localhost:8080/api/cart";

export const addToCart = async (foodId,token) => {
   try {
    await axios.post(API_URL,{foodId},{headers : {"Authorization" : `Bearer ${token}`}});
   } catch (error) {
    throw error;
   }
};

export const removeQtyFromCart = async (foodId,token) => {
    try {
        await axios.post(API_URL+"/remove",{foodId},{headers : {"Authorization" : `Bearer ${token}`}});
    } catch (error) {
        throw error;
    }
 };

 export const getCartData = async (token) => {
    try {
        const response = await axios.get(API_URL,{headers : {"Authorization" : `Bearer ${token}`}});
        return response.data.items;
    } catch (error) {
        throw error;
    }
 };