/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.webkit;

import android.os.Handler;

/** {@collect.stats}
 * {@description.open}
 * SslErrorHandler: class responsible for handling SSL errors.
 * This class is passed as a parameter to BrowserCallback.displaySslErrorDialog
 * and is meant to receive the user's response.
 * {@description.close}
 */
public class SslErrorHandler extends Handler {

    /** {@collect.stats}
     * @hide Only for use by WebViewProvider implementations.
     */
    public SslErrorHandler() {}

    /** {@collect.stats}
	 * {@description.open}
     * Proceed with the SSL certificate.
	 * {@description.close}
     */
    public void proceed() {}

    /** {@collect.stats}
	 * {@description.open}
     * Cancel this request and all pending requests for the WebView that had
     * the error.
	 * {@description.close}
     */
    public void cancel() {}
}
