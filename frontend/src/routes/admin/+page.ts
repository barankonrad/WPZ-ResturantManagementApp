import type { PageLoad } from "./$types";

export const load: PageLoad = async () => {
  return {
    menu: menuMock()
  };
};

const menuMock = () => [
  {
    id: 1,
    name: "Margherita Pizza",
    price: 12.99,
    imageUrl:
      "https://images.pexels.com/photos/31708264/pexels-photo-31708264.jpeg?cs=srgb&dl=pexels-damla-selen-demir-429137893-31708264.jpg&fm=jpg",
    attribution: "Photo by Damla-Selen Demir on Pexels"
  },
  {
    id: 2,
    name: "Classic Cheeseburger",
    price: 11.99,
    imageUrl:
      "https://images.pexels.com/photos/19247567/pexels-photo-19247567.jpeg?cs=srgb&dl=pexels-jonathanborba-19247567.jpg&fm=jpg",
    attribution: "Photo by Jonathan Borba on Pexels"
  },
  {
    id: 3,
    name: "Salmon Nigiri Sushi",
    price: 14.99,
    imageUrl:
      "https://images.pexels.com/photos/28992198/pexels-photo-28992198.jpeg?cs=srgb&dl=pexels-pedrofurtadoo-28992198.jpg&fm=jpg",
    attribution: "Photo by Pedro Furtado on Pexels"
  },
  {
    id: 4,
    name: "Caesar Salad",
    price: 9.99,
    imageUrl:
      "https://images.pexels.com/photos/29935340/pexels-photo-29935340.jpeg?cs=srgb&dl=pexels-khezez-29935340.jpg&fm=jpg",
    attribution: "Photo by khezez on Pexels"
  },
  {
    id: 5,
    name: "Spaghetti Bolognese",
    price: 13.99,
    imageUrl:
      "https://images.pexels.com/photos/28575312/pexels-photo-28575312.jpeg?cs=srgb&dl=pexels-marceloverfe-28575312.jpg&fm=jpg",
    attribution: "Photo by Marcelo Verfe on Pexels"
  },
  {
    id: 6,
    name: "Chicken Tacos",
    price: 10.99,
    imageUrl:
      "https://images.pexels.com/photos/20348820/pexels-photo-20348820.jpeg?cs=srgb&dl=pexels-armando-h-210148640-20348820.jpg&fm=jpg",
    attribution: "Photo by Armando H. on Pexels"
  },
  {
    id: 7,
    name: "Tonkotsu Ramen",
    price: 12.99,
    imageUrl:
      "https://images.pexels.com/photos/29989995/pexels-photo-29989995.jpeg?cs=srgb&dl=pexels-dbaler-29989995.jpg&fm=jpg",
    attribution: "Photo by Deane Bayas on Pexels"
  },
  {
    id: 8,
    name: "Ribeye Steak",
    price: 24.99,
    imageUrl:
      "https://images.pexels.com/photos/6542788/pexels-photo-6542788.jpeg?cs=srgb&dl=pexels-geraud-pfeiffer-6542788.jpg&fm=jpg",
    attribution: "Photo by Geraud Pfeiffer on Pexels"
  },
  {
    id: 9,
    name: "Avocado Toast",
    price: 8.99,
    imageUrl:
      "https://images.pexels.com/photos/566566/pexels-photo-566566.jpeg?cs=srgb&dl=pexels-foodie-factor-162291-566566.jpg&fm=jpg",
    attribution: "Photo by Foodie Factor on Pexels"
  },
  {
    id: 10,
    name: "Vanilla Ice Cream",
    price: 4.99,
    imageUrl:
      "https://images.pexels.com/photos/161420/pexels-photo-161420.jpeg?cs=srgb&dl=pexels-pixabay-161420.jpg&fm=jpg",
    attribution: "Photo by Pixabay on Pexels"
  },
  {
    id: 11,
    name: "Caff\u00e8 Latte",
    price: 3.99,
    imageUrl:
      "https://images.pexels.com/photos/31780547/pexels-photo-31780547.jpeg?cs=srgb&dl=pexels-verolova-31780547.jpg&fm=jpg",
    attribution: "Photo by V\u00e9ro Lova on Pexels"
  },
  {
    id: 12,
    name: "Fresh Orange Juice",
    price: 4.99,
    imageUrl:
      "https://images.pexels.com/photos/158053/pexels-photo-158053.jpeg?cs=srgb&dl=pexels-pixabay-158053.jpg&fm=jpg",
    attribution: "Photo by Pixabay on Pexels"
  }
];
