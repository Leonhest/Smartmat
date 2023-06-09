import axios from "axios";

const BASE_API_URL = "https://kassal.app/api/v1";

export const getItems = async (searchQuery) => {
  const response = axios.get(
    `https://kassal.app/api/v1/products?search=${searchQuery}`,
    {
      headers: {
        Authorization: `Bearer lWLt2onXRYSUgtMTkeJQq5i4dP6XhHPkl7ywLOSX`,
      },
    }
  );
  return (await response).data.data;
};

export const getItemsByPage = async (searchQuery, pageNr) => {
    const response = axios.get(
        `https://kassal.app/api/v1/products?search=${searchQuery}&page=${pageNr}`,
        {
            headers: {
                Authorization: `Bearer lWLt2onXRYSUgtMTkeJQq5i4dP6XhHPkl7ywLOSX`,
            },
        }
    );
    return (await response).data.data;
};


export const getItemByBarcode = async (barcode) => {
  const response = axios.get(
    `https://kassal.app/api/v1/products/ean/${barcode}`,
    {
      headers: {
        Authorization: `Bearer lWLt2onXRYSUgtMTkeJQq5i4dP6XhHPkl7ywLOSX`,
      },
    }
  );
  return (await response).data.data;
};
