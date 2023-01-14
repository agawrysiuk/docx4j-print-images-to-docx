import { Injectable } from "@angular/core";

@Injectable()
export class StringService {

    private imgSrcRegex = new RegExp("src\s*=\s*\"(.+?)\"", "g")
    private aHrefRegex = new RegExp("href\s*=\s*\"(.+?)\"", "g")

    getPicsFromText(pastedText: string): string[] {
        return Array.from(pastedText.matchAll(this.imgSrcRegex), x => x[1]);
    }

    getHrefsFromText(pastedText: string): string[] {
        return Array.from(pastedText.matchAll(this.aHrefRegex), x => x[1]);
    }
}