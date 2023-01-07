export interface Card {
    id: string;
    uri: string;
    name: string;
    image_uris?: ImageUris;
    card_faces?: CardFaces[];
}

interface ImageUris {
    large: string;
}

interface CardFaces {
    image_uris: ImageUris;
}
